package com.parizmat.config

import com.parizmat.controller.routing.authenticate
import com.parizmat.controller.routing.getSecretInfo
import com.parizmat.controller.routing.signIn
import com.parizmat.controller.routing.signUp
import com.parizmat.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.routingConfig(
    service: UserService
) {
    routing {
        signIn(service)
        signUp(service)
        authenticate()
        getSecretInfo()
    }
}