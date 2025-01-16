package com.itami.workout_flow.data.model.user

data class CreateUser(
    val googleId: String?,
    val email: String,
    val hashPassword: String?,
    val name: String,
    val username: String,
    val profilePictureUrl: String?,
    val subscription: Subscription,
    val isActive: Boolean,
)