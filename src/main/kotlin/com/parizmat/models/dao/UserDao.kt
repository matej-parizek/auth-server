package com.parizmat.models.dao

interface UserDao {
    /**
     * Unique username
     */
    val username: String
    /**
     * Hashed password
     */
    val password: String


    /**
     * Unique id
     */
    val id: String
}