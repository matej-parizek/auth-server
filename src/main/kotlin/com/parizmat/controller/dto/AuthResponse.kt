package com.parizmat.controller.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)