package com.parizmat

import com.parizmat.config.routingConfig
import com.parizmat.config.securityConfig
import com.parizmat.config.serializationConfig
import com.parizmat.service.UserService
import io.kotest.core.config.AbstractProjectConfig
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.testing.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject


object ProjectConfig : AbstractProjectConfig() {
    override suspend fun beforeProject() {
        startKoin {
            modules(dependencyModule)
        }
    }

    override suspend fun afterProject() {
        stopKoin()
    }
}
