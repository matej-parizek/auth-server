package com.parizmat
import com.parizmat.repository.UserRepository
import com.parizmat.repository.UserRepositoryMongoDB
import com.parizmat.security.hash.ARGON2HashService
import com.parizmat.security.hash.HashService
import com.parizmat.service.JwtTokenService
import com.parizmat.service.TokenService
import com.parizmat.service.UserService
import com.parizmat.service.UserServiceImp
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.testcontainers.containers.MongoDBContainer

val dependencyModule = module {
    single<MongoDBContainer> { MongoDBContainer("mongo:latest").apply { start() } }

    single<CoroutineDatabase> {
        val mongoContainer = get<MongoDBContainer>()
        KMongo.createClient(mongoContainer.replicaSetUrl).coroutine.getDatabase("test")
    }

    factory<UserRepository> { UserRepositoryMongoDB(get()) }
    factory<HashService> { ARGON2HashService() }
    factory<TokenService> { JwtTokenService() }
    factory<UserService> { UserServiceImp(get(), get(), get()) }
}
