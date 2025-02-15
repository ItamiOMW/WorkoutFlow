package com.itami.workout_flow.data.model.user

data class UpsertUser(
    val firebaseUID: String?,
    val email: String,
    val passwordHash: String?,
    val name: String,
    val username: String,
    val profilePictureUrl: String?,
    val isActive: Boolean,
)