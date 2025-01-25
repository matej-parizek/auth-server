package com.parizmat.config

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.serializationConfig(){
    install(ContentNegotiation){
        json()
    }
}