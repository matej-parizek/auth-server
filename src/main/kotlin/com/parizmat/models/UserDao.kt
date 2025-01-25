package com.parizmat.models

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
     * hash salt
     */
    val salt: String
}