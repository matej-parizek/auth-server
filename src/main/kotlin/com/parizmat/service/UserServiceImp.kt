package com.parizmat.service

import com.parizmat.repository.UserRepository
import com.parizmat.security.hash.HashService
import arrow.core.Either
import com.parizmat.mapper.toUserDao
import com.parizmat.models.domain.HashImp
import com.parizmat.models.domain.TokenClaim
import com.parizmat.models.domain.User

class UserServiceImp(
    private val hashService: HashService,
    private val repository: UserRepository,
    private val tokenService: TokenService
) : UserService {
    override suspend fun signUp(user: User): Either<AuthError,Unit> {
        repository.findUserByUsername(user.username.uppercase())?.let {
            return Either.Left(AuthError.UsernameAlreadyExists)
        }
        val hash = hashService.hash(user.password)
        return if(repository.insertUser(user.toUserDao(hash))) Either.Right(Unit)
        else Either.Left(AuthError.UserNotFound)
    }

    override suspend fun signIn(user: User): Either<AuthError,String> {
        return repository.findUserByUsername(user.username.uppercase())?.let { entity ->
            val hash = HashImp( hash =  entity.password)
            return if (hashService.verify(user.password, hash)) {
                Either.Right(tokenService.generateToken(
                    TokenClaim(
                        name = "userId",
                        value = entity.id
                    )
                ))
            } else {
                Either.Left(AuthError.PasswordIncorrect)
            }
        }?: Either.Left(AuthError.UserNotFound)
    }
}