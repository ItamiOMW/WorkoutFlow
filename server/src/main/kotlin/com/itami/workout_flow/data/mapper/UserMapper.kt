package com.itami.workout_flow.data.mapper

import com.itami.workout_flow.data.database.entity.UserEntity
import com.itami.workout_flow.data.model.user.Subscription
import com.itami.workout_flow.data.model.user.UpsertUser
import com.itami.workout_flow.data.model.user.User
import com.itami.workout_flow.dto.response.SubscriptionResponse
import com.itami.workout_flow.dto.response.UserProfileResponse
import com.itami.workout_flow.dto.response.UserResponse
import com.itami.workout_flow.model.UserProfileSubscription

fun UserEntity.toUser() = User(
    id = this.id.value,
    firebaseUID = this.firebaseUID,
    email = this.email,
    passwordHash = this.passwordHash,
    name = this.name,
    username = this.username,
    profilePictureUrl = this.profilePicUrl,
    subscription = Subscription.Basic, // Not implemented yet
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
)

fun User.toUpsertUser() = UpsertUser(
    firebaseUID = this.firebaseUID,
    email = this.email,
    passwordHash = this.passwordHash,
    name = this.name,
    username = this.username,
    profilePictureUrl = this.profilePictureUrl,
    isActive = this.isActive
)

fun User.toUserResponse() = UserResponse(
    id = this.id,
    email = this.email,
    name = this.name,
    username = this.username,
    profilePictureUrl = this.profilePictureUrl,
    subscription = this.subscription.toUserSubscriptionResponse(),
    createdAt = this.createdAt.toString()
)

fun Subscription.toUserSubscriptionResponse(): SubscriptionResponse {
    return when (val subscription = this) {
        is Subscription.Basic -> {
            SubscriptionResponse.Basic
        }

        is Subscription.Premium -> {
            SubscriptionResponse.Premium(subscription.expirationDateTime.toString())
        }
    }
}

fun User.toUserProfileResponse() = UserProfileResponse(
    id = this.id,
    name = this.name,
    username = this.username,
    profilePictureUrl = this.profilePictureUrl,
    subscription = this.subscription.toUserProfileSubscription(),
)

fun Subscription.toUserProfileSubscription(): UserProfileSubscription {
    return when (val subscription = this) {
        is Subscription.Basic -> UserProfileSubscription.BASIC
        is Subscription.Premium -> UserProfileSubscription.PREMIUM
    }
}