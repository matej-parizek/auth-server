package com.parizmat

import com.parizmat.config.routingConfig
import com.parizmat.config.securityConfig
import com.parizmat.config.serializationConfig
import com.parizmat.di.appModule
import com.parizmat.service.UserService
import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)


fun Application.module() {
    install(Koin) {
        modules(appModule)
    }
    securityConfig()
    serializationConfig()
    val userService: UserService by inject()
    routingConfig(userService)
}
