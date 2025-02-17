package com.itami.workout_flow.core.data.local.database.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itami.workout_flow.model.UserProfileSubscription

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val username: String,
    val profilePictureUrl: String?,
    val subscription: UserProfileSubscription,
)