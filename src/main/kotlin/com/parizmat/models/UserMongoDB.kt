package com.parizmat.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class UserMongoDB(
    override val username: String,
    override val password: String,
    override val salt: String,
    @BsonId val id: ObjectId = ObjectId()
) : UserDao