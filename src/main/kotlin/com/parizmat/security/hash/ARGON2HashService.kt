package com.parizmat.security.hash

import com.parizmat.models.domain.Hash
import com.parizmat.models.domain.HashImp
import de.mkammerer.argon2.Argon2
import de.mkammerer.argon2.Argon2Factory
import java.security.SecureRandom

class ARGON2HashService(
    private val saltLength: Int = 32,
    private val argon2: Argon2 = Argon2Factory.create()
) : HashService {

    override fun hash(password: String): Hash {
        val salt = ByteArray(saltLength)
        SecureRandom().nextBytes(salt)
        val hash = argon2.hash(2, 65536, 1, password.toCharArray(), Charsets.UTF_8)
        return HashImp(
            hash = hash,
        )
    }

    override fun verify(password: String, hash: Hash): Boolean {
        return argon2.verify(hash.hash, password.toCharArray())
    }
}
