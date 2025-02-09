package com.parizmat.controller.routing

import arrow.core.Either
import com.parizmat.controller.dto.AuthRequest
import com.parizmat.controller.dto.AuthResponse
import com.parizmat.mapper.toUser
import com.parizmat.service.AuthError
import com.parizmat.service.UserService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.signUp(
    service: UserService,
){
    post("/sign-up"){
       runCatching {
           call.receive<AuthRequest>()
       }.onSuccess {
           if(it.username.isEmpty() || it.password.isEmpty()){
               call.respond(HttpStatusCode.BadRequest, "Invalid request data, username or password is empty")
               return@post
           }

           return@post when(val result = service.signUp(it.toUser())) {
               is Either.Right -> call.respond(HttpStatusCode.Created, "User created")
               is Either.Left -> when(result.value){
                   is AuthError.UserNotFound -> call.respond(HttpStatusCode.NotFound, "User not found")
                   is AuthError.UsernameAlreadyExists -> call.respond(HttpStatusCode.Conflict, "Username already exists")
                   else -> call.respond(HttpStatusCode.InternalServerError, "Internal server error: ${result.value}")
               }
           }

       }.onFailure {
           call.respond(HttpStatusCode.BadRequest, "Invalid request data: ${it.message}")
       }
    }
}

fun Route.signIn(
    service: UserService,
){
    post("/sign-in"){
        kotlin.runCatching {
            call.receive<AuthRequest>()
        }.onSuccess {
            if(it.username.isEmpty() || it.password.isEmpty()){
                call.respond(HttpStatusCode.BadRequest, "Invalid request data, username or password is empty")
                return@post
            }
            try {
                when (val response = service.signIn(it.toUser())) {
                    is Either.Right -> call.respond(HttpStatusCode.OK, AuthResponse(response.value))
                    is Either.Left -> {
                        when (response.value) {
                            is AuthError.UserNotFound -> call.respond(HttpStatusCode.NotFound, "User not found")
                            is AuthError.PasswordIncorrect -> call.respond(HttpStatusCode.Conflict, "Wrong password")
                            else -> call.respond(
                                HttpStatusCode.InternalServerError,
                                "Internal server error: ${response.value}"
                            )
                        }
                    }
                }
            }catch (e: Exception){
                call.respond(HttpStatusCode.InternalServerError, "Internal server error: ${e.message}")
            }
        }.onFailure {
            call.respond(HttpStatusCode.BadGateway, "Invalid request data: ${it.message}")
        }
    }
}

fun Route.authenticate(){
    authenticate{
        get("/authenticate"){
            call.respond(HttpStatusCode.OK, "Authenticated")
        }
    }
}

fun Route.getSecretInfo() {
    authenticate {
        get("/secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            call.respond(HttpStatusCode.OK, "Your userId is $userId")
        }
    }
}