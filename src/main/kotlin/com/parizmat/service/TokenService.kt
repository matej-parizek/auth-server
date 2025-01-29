package com.parizmat.service

import com.parizmat.models.domain.TokenClaim

interface TokenService {
    suspend fun generateToken(vararg claims: TokenClaim): String
}