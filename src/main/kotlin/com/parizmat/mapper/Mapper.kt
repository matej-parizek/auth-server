package com.parizmat.mapper

import com.parizmat.controller.dto.AuthRequest
import com.parizmat.models.*

fun AuthRequest.toUser() = UserImp(
    username = username,
    password = password,
)


fun User.toUserDto() = AuthRequest(
    username = username,
    password = password
)

fun UserDao.toUser() = UserImp(
    username = username,
    password = password
)

fun User.toUserDao(hash: Hash) = UserMongoDB(
    username = username,
    password = hash.hash,
    salt = hash.salt
)