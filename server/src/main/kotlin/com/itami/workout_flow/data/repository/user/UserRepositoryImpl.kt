package com.itami.workout_flow.data.repository.user

import com.itami.workout_flow.data.database.DatabaseFactory
import com.itami.workout_flow.data.database.entity.UserEntity
import com.itami.workout_flow.data.database.table.Users
import com.itami.workout_flow.data.mapper.toUser
import com.itami.workout_flow.data.model.user.UpsertUser
import com.itami.workout_flow.data.model.user.User
import com.itami.workout_flow.utils.AppError

class UserRepositoryImpl : UserRepository {

    override suspend fun getUserById(id: Long): User? {
        return DatabaseFactory.dbQuery {
            UserEntity
                .findById(id)
                ?.toUser()
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return DatabaseFactory.dbQuery {
            UserEntity
                .find { Users.email eq email }
                .firstOrNull()
                ?.toUser()
        }
    }

    override suspend fun getUserByUsername(username: String): User? {
        return DatabaseFactory.dbQuery {
            UserEntity
                .find { Users.username eq username }
                .firstOrNull()
                ?.toUser()
        }
    }

    override suspend fun createUser(upsertUser: UpsertUser): User {
        return DatabaseFactory.dbQuery {
            UserEntity.new {
                this.email = upsertUser.email
                this.passwordHash = upsertUser.passwordHash
                this.name = upsertUser.name
                this.username = upsertUser.username
                this.profilePicUrl = upsertUser.profilePictureUrl
                this.isActive = upsertUser.isActive
            }.toUser()
        }
    }

    override suspend fun updateUser(userId: Long, upsertUser: UpsertUser): User {
        return DatabaseFactory.dbQuery {
            UserEntity
                .findByIdAndUpdate(id = userId) { entity ->
                    entity.firebaseUID = upsertUser.firebaseUID
                    entity.email = upsertUser.email
                    entity.passwordHash = upsertUser.passwordHash
                    entity.name = upsertUser.name
                    entity.username = upsertUser.username
                    entity.profilePicUrl = upsertUser.profilePictureUrl
                    entity.isActive = upsertUser.isActive
                }
                ?.toUser()
                ?: throw Exception("User with this ID does not exist.")
        }
    }

}