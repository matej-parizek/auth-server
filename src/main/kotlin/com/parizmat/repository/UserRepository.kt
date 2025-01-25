package com.parizmat.repository

import com.parizmat.models.UserDao

interface UserRepository<T : UserDao> {
    suspend fun findUserByUsername(username: String): T?
    suspend fun insertUser(user: T) : Boolean
}