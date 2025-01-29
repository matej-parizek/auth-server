package com.parizmat.models.domain

data class UserImp(
    override val username: String,
    override val password: String
) : User