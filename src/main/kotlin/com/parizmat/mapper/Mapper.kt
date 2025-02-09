package com.parizmat.mapper

import com.parizmat.controller.dto.AuthRequest
import com.parizmat.models.dao.UserMongoDB
import com.parizmat.models.domain.Hash
import com.parizmat.models.domain.User
import com.parizmat.models.domain.UserImp

fun AuthRequest.toUser() = UserImp(
    username = username,
    password = password,
)

fun User.toUserDao(hash: Hash) = UserMongoDB(
    username = username.uppercase(),
    password = hash.hash,
)