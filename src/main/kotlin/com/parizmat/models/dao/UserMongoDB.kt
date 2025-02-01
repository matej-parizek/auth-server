package com.parizmat.models.dao

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class UserMongoDB(
    override val username: String,
    override val password: String,
    override val salt: String,
    @BsonId val _id: ObjectId = ObjectId(),
) : UserDao{
    override val id: String = _id.toString()
}