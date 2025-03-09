package com.itami.workout_flow.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutsResponse(
    val exercises: List<ExerciseResponse>,
    val authors: List<UserProfileResponse>,
    val workouts: List<WorkoutResponse>,
)