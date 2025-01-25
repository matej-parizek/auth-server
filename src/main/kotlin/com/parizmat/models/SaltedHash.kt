package com.parizmat.models

data class SaltedHash(
    override val hash: String,
    override val salt: String
): Hash
