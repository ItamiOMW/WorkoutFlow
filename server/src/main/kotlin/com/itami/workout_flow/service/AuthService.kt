package com.itami.workout_flow.service

import com.google.firebase.auth.FirebaseToken
import com.itami.workout_flow.data.mapper.toUpsertUser
import com.itami.workout_flow.data.model.user.UpsertUser
import com.itami.workout_flow.data.model.user.User
import com.itami.workout_flow.data.repository.user.UserRepository

class AuthService(
    private val userRepository: UserRepository,
) {

    suspend fun getUserWithFirebase(
        firebaseToken: FirebaseToken,
    ): User {
        val userByEmail = userRepository.getUserByEmail(firebaseToken.email)

        if (userByEmail == null) {
            val user = userRepository.createUser(
                UpsertUser(
                    firebaseUID = firebaseToken.uid,
                    email = firebaseToken.email,
                    passwordHash = null,
                    name = firebaseToken.name,
                    username = generateUniqueUsername(baseName = firebaseToken.name),
                    profilePictureUrl = firebaseToken.picture,
                    isActive = true
                )
            )
            return user
        }

        if (userByEmail.firebaseUID == null) {
            val updatedUser =  userRepository.updateUser(
                userId = userByEmail.id,
                upsertUser = userByEmail.toUpsertUser().copy(firebaseUID = firebaseToken.uid)
            )
            return updatedUser
        }

        return userByEmail
    }

    private suspend fun generateUniqueUsername(
        baseName: String,
        maxLength: Int = 24,
    ): String {
        val sanitizedBase = baseName
            .lowercase()
            .replace(Regex("[^a-z0-9]"), "") // Remove non-alphanumeric characters
            .take(maxLength)

        if (sanitizedBase.isEmpty()) return "user" + randomSuffix(length = 6)

        var username = sanitizedBase

        // If the base username is taken, generate new ones with a random suffix
        while (userRepository.getUserByUsername(username) != null) {
            val suffix = randomSuffix(length = 4)
            val availableLength = maxLength - suffix.length
            username = sanitizedBase.take(availableLength) + suffix
        }

        return username
    }

    private fun randomSuffix(length: Int): String {
        val chars = "abcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

}