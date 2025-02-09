package com.parizmat.com.parizmat.security.hash

import com.parizmat.models.domain.HashImp
import com.parizmat.security.hash.ARGON2HashService
import de.mkammerer.argon2.Argon2
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class ARGON2HashServiceTest : StringSpec({
    val mockArgon2 = mockk<Argon2>()
    val hashService = ARGON2HashService(argon2 = mockArgon2)

    "hash function generates expected hash" {
        val password = "password123"
        val expectedHash = "hashed_value"
        every { mockArgon2.hash(2, 65536, 1, password.toCharArray(), Charsets.UTF_8) } returns expectedHash

        val result = hashService.hash(password)

        result.hash shouldBe expectedHash
    }

    "verify function returns true for correct password" {
        val password = "password123"
        val hash = HashImp("correct_hash")
        every { mockArgon2.verify(hash.hash, password.toCharArray()) } returns true

        val result = hashService.verify(password, hash)

        result shouldBe true
    }

    "verify function returns false for incorrect password" {
        val password = "password123"
        val hash = HashImp("incorrect_hash")
        every { mockArgon2.verify(hash.hash, password.toCharArray()) } returns false

        val result = hashService.verify(password, hash)

        result shouldBe false
    }
})
