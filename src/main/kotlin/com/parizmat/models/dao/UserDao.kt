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
     * hash salt
     */
    val salt: String

    /**
     * Unique id
     */
    fun getId(): String
}