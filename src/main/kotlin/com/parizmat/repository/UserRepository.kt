package com.parizmat.repository

import com.parizmat.models.dao.UserDao

interface UserRepository{
    suspend fun findUserByUsername(username: String): UserDao?
    suspend fun insertUser(user: UserDao) : Boolean
}