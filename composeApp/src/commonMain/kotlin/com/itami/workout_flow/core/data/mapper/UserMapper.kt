package com.itami.workout_flow.core.data.mapper

import com.itami.workout_flow.core.data.local.database.entity.user.UserProfileEntity
import com.itami.workout_flow.core.domain.model.user.UserProfile
import com.itami.workout_flow.dto.response.UserProfileResponse

fun UserProfileEntity.toUserProfile() = UserProfile(
    id = this.id,
    name = this.name,
    username = this.username,
    profilePictureUrl = this.profilePictureUrl,
    subscription = this.subscription
)

fun UserProfileResponse.toUserProfile() = UserProfile(
    id = this.id,
    name = this.name,
    username = this.username,
    profilePictureUrl = this.profilePictureUrl,
    subscription = this.subscription
)

fun UserProfileResponse.toUserProfileEntity() = UserProfileEntity(
    id = this.id,
    name = this.name,
    username = this.username,
    profilePictureUrl = this.profilePictureUrl,
    subscription = this.subscription
)