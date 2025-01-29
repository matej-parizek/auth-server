package com.parizmat.security.hash

import com.parizmat.models.domain.Hash

interface HashService {
    fun hash(password: String): Hash
    fun verify(password: String, hash: Hash): Boolean
}