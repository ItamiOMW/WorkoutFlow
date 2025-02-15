package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.Users
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(Users)

    var firebaseUID by Users.firebaseUID
    var email by Users.email
    var passwordHash by Users.passwordHash
    var name by Users.name
    var username by Users.username
    var profilePicUrl by Users.profilePictureUrl
    var isActive by Users.isActive
    var createdAt by Users.createdAt
    var updatedAt by Users.updatedAt
}