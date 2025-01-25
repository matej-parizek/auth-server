package com.parizmat.security.hash

import com.parizmat.models.Hash
import com.parizmat.models.SaltedHash
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

class SHA256HashService(
    private val saltLength: Int = 32
) : HashService {
    override fun hash(password: String): Hash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        val hash = Hex.encodeHex(salt)
        return SaltedHash(
            DigestUtils.sha256Hex("$hash$password"),
            String(hash)
        )
    }

    override fun verify(password: String, hash: Hash): Boolean {
        return DigestUtils.sha256Hex(hash.salt + password) == hash.hash
    }
}