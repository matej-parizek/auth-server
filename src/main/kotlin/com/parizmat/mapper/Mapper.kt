package com.parizmat.mapper

import com.parizmat.controller.dto.AuthRequest
import com.parizmat.controller.dto.AuthResponse
import com.parizmat.models.dao.UserDao
import com.parizmat.models.dao.UserMongoDB
import com.parizmat.models.domain.Hash
import com.parizmat.models.domain.TokenClaim
import com.parizmat.models.domain.User
import com.parizmat.models.domain.UserImp

fun AuthRequest.toUser() = UserImp(
    username = username,
    password = password,
)

fun User.toUserDao(hash: Hash) = UserMongoDB(
    username = username,
    password = hash.hash,
    salt = hash.salt
)