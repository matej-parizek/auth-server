package com.parizmat.repository

import com.parizmat.models.dao.UserDao
import com.parizmat.models.dao.UserMongoDB
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq


class UserRepositoryMongoDB(db: CoroutineDatabase) : UserRepository {
    private val users = db.getCollection<UserMongoDB>()

    override suspend fun findUserByUsername(username: String): UserDao? {
       return users.findOne(UserMongoDB::username eq username)
    }

    override suspend fun insertUser(user: UserDao): Boolean {
        return users.insertOne(user as UserMongoDB).wasAcknowledged()
    }
}