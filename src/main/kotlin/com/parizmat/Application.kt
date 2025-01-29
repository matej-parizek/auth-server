package com.parizmat

import com.parizmat.config.routingConfig
import com.parizmat.config.securityConfig
import com.parizmat.config.serializationConfig
import com.parizmat.repository.UserRepository
import com.parizmat.repository.UserRepositoryMongoDB
import com.parizmat.security.hash.SHA256HashService
import com.parizmat.service.JwtTokenService
import com.parizmat.service.UserServiceImp
import io.ktor.server.application.*
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.coroutine


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)


fun Application.module() {
    val db = KMongo.createClient(connectionString = System.getenv("mongo.uri"))
        .coroutine
        .getDatabase(System.getenv("mongo.database"))
    val repo : UserRepository = UserRepositoryMongoDB(db)
    val hashService = SHA256HashService()
    val tokenService = JwtTokenService()
    val userService = UserServiceImp(hashService, repo, tokenService)

    securityConfig()
    serializationConfig()
    routingConfig(userService)
}
