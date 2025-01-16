package com.itami.workout_flow.core.domain.model.workout

import com.itami.workout_flow.core.domain.model.user.UserProfile
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutType

data class Workout(
    val id: String,
    val author: UserProfile?,
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
    val isCreatedByCurrentUser: Boolean,
    val createdAt: String,
)
