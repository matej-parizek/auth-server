package com.parizmat.service

import com.parizmat.models.UserDao
import com.parizmat.models.UserMongoDB
import com.parizmat.repository.UserRepository
import com.parizmat.security.hash.HashService
import arrow.core.Either
import com.parizmat.mapper.toUserDao
import com.parizmat.models.User

class UserServiceImp(
    private val hashService: HashService,
    private val repository: UserRepository<UserDao>
) : UserService {
    override suspend fun signUp(user: User): Boolean {
        val hash = hashService.hash(user.password)
        return repository.insertUser(user.toUserDao(hash))
    }

    override suspend fun signIn(user: User): Either<AuthError,String> {
        return repository.findUserByUsername(user.username)?.let {
            val hash = hashService.hash(user.password)
            return if (hashService.verify(user.password, hash)) {
                Either.Right(user.username)
            } else {
                Either.Left(AuthError.PasswordIncorrect)
            }
        }?: Either.Left(AuthError.UserNotFound)
    }
}