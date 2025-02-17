package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.DatabaseView
import androidx.room.Relation
import com.itami.workout_flow.core.data.local.database.entity.user.UserProfileEntity

@DatabaseView(
    """
    SELECT 
        w.clientUUID,
        w.name,
        w.durationMin,
        w.favoritesCount,
        w.isFavorite,
        w.isCreatedByCurrentUser
    FROM workouts w
    LEFT JOIN workout_muscles wm ON wm.workoutUUID = w.clientUUID
    LEFT JOIN user_profiles up ON up.id = w.authorId
"""
)
data class WorkoutPreviewDbView(
    val clientUUID: String,
    val name: String,
    val authorId: Long,
    val durationMin: Int,
    val favoritesCount: Int,
    val isFavorite: Boolean,
    val isCreatedByCurrentUser: Boolean,
    @Relation(
        entity = WorkoutMuscleEntity::class,
        parentColumn = "clientUUID",
        entityColumn = "workoutUUID"
    )
    val workoutMuscles: List<WorkoutMuscleEntity>,
    @Relation(
        entity = UserProfileEntity::class,
        parentColumn = "authorId",
        entityColumn = "id"
    )
    val authorProfileEntity: UserProfileEntity,
)