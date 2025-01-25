package com.parizmat.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.parizmat.models.TokenClaim
import java.time.Instant

class JwtTokenService : TokenService {
    override suspend fun generateToken(vararg claims: TokenClaim): String {
        val token =  JWT.create()
            .withAudience(System.getenv("JWT_AUDIENCE"))
            .withIssuer(System.getenv("JWT_ISSUER"))
            .withExpiresAt(Instant.now().plusMillis( System.getenv("JWT_EXPIRATION").toLong()))
        claims.forEach {
            token.withClaim(it.name, it.value)
        }
        return token.sign(Algorithm.HMAC256(System.getenv("JWT_SECRET")))
    }
}