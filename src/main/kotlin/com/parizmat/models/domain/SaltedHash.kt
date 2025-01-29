package com.parizmat.models.domain

data class SaltedHash(
    override val hash: String,
    override val salt: String
): Hash
