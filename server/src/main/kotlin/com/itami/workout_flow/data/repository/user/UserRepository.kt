package com.itami.workout_flow.data.repository.user

import com.itami.workout_flow.data.model.user.UpsertUser
import com.itami.workout_flow.data.model.user.User

interface UserRepository {

    suspend fun getUserById(id: Long): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun getUserByUsername(username: String): User?

    suspend fun createUser(upsertUser: UpsertUser): User

    suspend fun updateUser(userId: Long, upsertUser: UpsertUser): User

}