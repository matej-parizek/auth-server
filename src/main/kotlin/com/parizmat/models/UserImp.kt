package com.parizmat.models

data class UserImp(
    override val username: String,
    override val password: String
) : User