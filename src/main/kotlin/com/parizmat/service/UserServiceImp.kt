package com.parizmat.service

import com.parizmat.repository.UserRepository
import com.parizmat.security.hash.HashService
import arrow.core.Either
import com.parizmat.mapper.toUserDao
import com.parizmat.models.domain.SaltedHash
import com.parizmat.models.domain.TokenClaim
import com.parizmat.models.domain.User
import org.litote.kmongo.util.idValue

class UserServiceImp(
    private val hashService: HashService,
    private val repository: UserRepository,
    private val tokenService: TokenService
) : UserService {
    override suspend fun signUp(user: User): Boolean {
        val hash = hashService.hash(user.password)
        return repository.insertUser(user.toUserDao(hash))
    }

    override suspend fun signIn(user: User): Either<AuthError,String> {
        return repository.findUserByUsername(user.username)?.let { entity ->
            val hash = SaltedHash(salt = entity.salt, hash =  entity.password)
            return if (hashService.verify(user.password, hash)) {
                Either.Right(tokenService.generateToken(
                    TokenClaim(
                        name = "userId",
                        value = entity.getId()
                    )
                ))
            } else {
                Either.Left(AuthError.PasswordIncorrect)
            }
        }?: Either.Left(AuthError.UserNotFound)
    }
}