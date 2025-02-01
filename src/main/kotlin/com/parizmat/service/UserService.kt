package com.parizmat.service

import arrow.core.Either
import com.parizmat.models.domain.User

interface UserService {
    /**
     * Sign up a new user
     */
    suspend fun signUp(user: User): Either<AuthError,Unit>

    /**
     * Sign in a user
     */
    suspend fun signIn(user: User): Either<AuthError, String>
}

sealed interface AuthError {
    data object UserNotFound: AuthError
    data object PasswordIncorrect: AuthError
    data object UsernameAlreadyExists: AuthError
}