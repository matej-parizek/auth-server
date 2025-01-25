package com.parizmat.service

import com.parizmat.models.UserDao
import arrow.core.Either
import com.parizmat.models.User

interface UserService {
    /**
     * Sign up a new user
     */
    suspend fun signUp(user: User): Boolean

    /**
     * Sign in a user
     */
    suspend fun signIn(user: User): Either<AuthError,String>
}

sealed interface AuthError {
    data object UserNotFound: AuthError
    data object PasswordIncorrect: AuthError
}