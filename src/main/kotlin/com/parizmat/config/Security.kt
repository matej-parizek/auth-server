package com.parizmat.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.securityConfig() {
    authentication {
        jwt {
            realm = System.getenv("jwt.realm")
            verifier(
                JWT.require(Algorithm.HMAC256(System.getenv("jwt.secret")))
                    .withAudience(System.getenv("jwt.audience"))
                    .withIssuer(System.getenv("jwt.issuer"))
                    .build()
            )
            validate { jwtCredential ->
                if(jwtCredential.payload.audience.contains(System.getenv("jwt.audience")))
                    JWTPrincipal(jwtCredential.payload) else null
            }
        }
    }
}