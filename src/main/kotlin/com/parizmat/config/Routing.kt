package com.parizmat.config

import com.parizmat.controller.routing.authenticate
import com.parizmat.controller.routing.getSecretInfo
import com.parizmat.controller.routing.singIn
import com.parizmat.controller.routing.singUp
import com.parizmat.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    user: UserService
) {
    routing {
        singIn(user)
        singUp(user)
        authenticate()
        getSecretInfo()
    }
}