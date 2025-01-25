package com.parizmat.repository

import com.parizmat.models.UserMongoDB
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq


class UserRepositoryMongoDB(db: CoroutineDatabase) : UserRepository<UserMongoDB> {
    private val users = db.getCollection<UserMongoDB>()

    override suspend fun findUserByUsername(username: String): UserMongoDB? {
       return users.findOne(UserMongoDB::username eq username)
    }

    override suspend fun insertUser(user: UserMongoDB) : Boolean{
        return users.insertOne(user).wasAcknowledged()
    }
}