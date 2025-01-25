package com.parizmat.service

import com.parizmat.models.TokenClaim

interface TokenService {
    suspend fun generateToken(vararg claims: TokenClaim): String
}