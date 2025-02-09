package com.parizmat.service

import arrow.core.Either
import com.parizmat.models.dao.UserMongoDB
import com.parizmat.models.domain.HashImp
import com.parizmat.models.domain.UserImp
import com.parizmat.repository.UserRepository
import com.parizmat.security.hash.HashService
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify


internal class UserServiceImpTest : ShouldSpec({

    val hashService = mockk<HashService>(relaxed = true)
    val repository = mockk<UserRepository>(relaxed = true)
    val tokenService = mockk<TokenService>(relaxed = true)
    val userService = UserServiceImp(hashService, repository, tokenService)

    context("signUp functionality") {
        should("return UsernameAlreadyExists if user already exists") {
            val user = UserImp(username = "username", password = "password")
            coEvery { repository.findUserByUsername("USERNAME") } returns UserMongoDB(
                username = "username",
                password = "password"
            )


            val result = userService.signUp(user)

            result shouldBe Either.Left(AuthError.UsernameAlreadyExists)
            verify(exactly = 1) { runBlocking { repository.findUserByUsername("USERNAME") } }
            verify { hashService wasNot Called }
            verify { tokenService wasNot Called }
        }

        should("insert user and return success for a new user") {

            val user = UserImp(username = "username", password = "password")

            coEvery { repository.findUserByUsername("USERNAME") } returns null
            coEvery { hashService.hash(any()) } returns HashImp("hashedpassword")
            coEvery { repository.insertUser(any()) } returns true

            val result = userService.signUp(user)

            result shouldBe Either.Right(Unit)
            verify { runBlocking { repository.insertUser(any()) } }
        }
    }

    context("signIn functionality") {
        should("return PasswordIncorrect if password is wrong") {
            val user = UserImp("username", "wrongpassword")
            val storedUser = UserMongoDB("username", "correcthash")
            coEvery { repository.findUserByUsername("USERNAME") } returns storedUser
            coEvery { hashService.verify("wrongpassword", any()) } returns false

            val result = userService.signIn(user)

            result shouldBe Either.Left(AuthError.PasswordIncorrect)
        }

        should("return token if password is correct") {
            val user = UserImp("username", "correctpassword")
            val storedUser = UserMongoDB("username", "correcthash")
            coEvery { repository.findUserByUsername("USERNAME") } returns storedUser
            coEvery { hashService.verify("correctpassword", any()) } returns true
            coEvery { tokenService.generateToken(any()) } returns "validToken"

            val result = userService.signIn(user)

            result shouldBe Either.Right("validToken")
            verify {
                runBlocking {
                    tokenService.generateToken(any())
                }
            }
        }
    }
})