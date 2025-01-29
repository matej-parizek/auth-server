package com.parizmat.security.hash

import com.parizmat.models.domain.Hash
import com.parizmat.models.domain.SaltedHash
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

class SHA256HashService(
    private val saltLength: Int = 32
) : HashService {
    override fun hash(password: String): Hash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        val hash = Hex.encodeHexString(salt)
        return SaltedHash(
            hash = DigestUtils.sha256Hex("$hash$password"),
            salt = hash
        )
    }

    override fun verify(password: String, hash: Hash): Boolean {
        return DigestUtils.sha256Hex(hash.salt + password) == hash.hash
    }
}