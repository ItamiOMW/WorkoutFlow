package com.itami.workout_flow.data.model.workout

import com.itami.workout_flow.data.model.user.User
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutType
import java.time.OffsetDateTime

data class Workout(
    val serverId: Long,
    val clientUUID: String,
    val author: User,
    val name: String,
    val description: String,
    val durationMin: Int,
    val workoutTypes: List<WorkoutType>,
    val equipments: List<Equipment>,
    val muscles: List<Muscle>,
    val workoutExercises: List<WorkoutExercise>,
    val favoritesCount: Int,
    val isFavorite: Boolean,
    val isPublic: Boolean,
    val isCreatedByUser: Boolean,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime?,
    val isFavoriteChangedAt: OffsetDateTime?
)