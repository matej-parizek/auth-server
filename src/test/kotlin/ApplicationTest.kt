package com.parizmat

import io.kotest.core.config.AbstractProjectConfig
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin


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
