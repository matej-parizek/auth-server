package com.parizmat.repository

import com.parizmat.MongoTestListener
import com.parizmat.models.dao.UserMongoDB
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.coroutine.CoroutineDatabase

internal class UserRepositoryMongoDBTest : ShouldSpec(), KoinComponent {
    private val client: CoroutineDatabase by inject()
    private val repository: UserRepository by inject()
    private val database: CoroutineDatabase by inject()

    init {
        listeners(MongoTestListener())

        val user = UserMongoDB( username = "username", password = "password")
        context("testing MongoDB repository") {
            should("insert a user") {
                runBlocking {
                    repository.insertUser(user)
                    val count = client.getCollection<UserMongoDB>().countDocuments()
                    count shouldBe 1L
                }
            }
            should("find a user by username") {
                runBlocking {
                    database.getCollection<UserMongoDB>().insertOne(user)
                    val foundUser = repository.findUserByUsername("username")
                    foundUser shouldBe user
                }
            }
            should("find a user by username that does not exist") {
                runBlocking {
                    val foundUser = repository.findUserByUsername("username")
                    foundUser shouldBe null
                }
            }
        }
    }
}
