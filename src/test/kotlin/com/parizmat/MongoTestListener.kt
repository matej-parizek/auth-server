package com.parizmat

import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.testcontainers.containers.MongoDBContainer

class MongoTestListener : TestListener, KoinComponent {
    private lateinit var mongoContainer: MongoDBContainer
    private lateinit var database: CoroutineDatabase


    override suspend fun beforeSpec(spec: Spec) {
        mongoContainer = get()
        database = get()
        mongoContainer.start()
    }

    override suspend fun afterSpec(spec: Spec) {
        mongoContainer.stop()
        stopKoin()
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        database.drop()
        super.afterTest(testCase, result)
    }
}
