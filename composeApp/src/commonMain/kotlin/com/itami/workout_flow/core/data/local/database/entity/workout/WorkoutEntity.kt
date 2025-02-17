package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.itami.workout_flow.core.data.local.database.entity.user.UserProfileEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Entity(
    tableName = "workouts",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["authorId"],
            onDelete = ForeignKey.NO_ACTION,
        )
    ],
    indices = [Index(value = ["authorId"])]
)
data class WorkoutEntity(
    @PrimaryKey
    val clientUUID: String,
    val serverId: Long? = null,
    val authorId: Long? = null,
    val name: String,
    val description: String,
    val durationMin: Int,
    val favoritesCount: Int = 0,
    val isFavorite: Boolean = false,
    val isPublic: Boolean = false,
    val isCreatedByCurrentUser: Boolean = false,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant? = null,
    val isFavoriteChangedAt: Instant? = null,
    val isSynced: Boolean = false,
)
