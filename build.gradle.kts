// Define property variables at the top
val kotlin_version: String by project
val ktor_version: String by project
val arrow_version: String by project
val kmongo_version: String by project
val logback_version: String by project
val kotlinx_coroutines_version: String by project
val commons_codec_version: String by project
val kotest_version_bom: String by project
val mongo_test_version: String by project
val koin_version: String by project
val argon2_version: String by project
val mockk_version: String by project

group = "com.parizmat"
version = "0.1.0"


plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

application{
    mainClass.set("com.parizmat.ApplicationKt")
}

repositories {
    mavenCentral()
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_coroutines_version")
    implementation("org.litote.kmongo:kmongo-async:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")
    implementation("io.arrow-kt:arrow-core:$arrow_version")
    implementation("io.arrow-kt:arrow-fx-coroutines:$arrow_version")
    implementation("commons-codec:commons-codec:$commons_codec_version")
    implementation("de.mkammerer:argon2-jvm:$argon2_version")

    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // Kotest
    testImplementation(platform("io.kotest:kotest-bom:$kotest_version_bom"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version_bom")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version_bom")

    // Testcontainers
    testImplementation("org.testcontainers:testcontainers:$mongo_test_version")
    testImplementation("org.testcontainers:mongodb:$mongo_test_version")

    // Koin
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    testImplementation("io.insert-koin:koin-test:$koin_version")

    //Mockk
    testImplementation("io.mockk:mockk:${mockk_version}")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
