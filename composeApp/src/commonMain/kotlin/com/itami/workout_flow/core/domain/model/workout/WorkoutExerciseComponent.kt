package com.itami.workout_flow.core.domain.model.workout

sealed interface WorkoutExerciseComponent {

    data class Single(val workoutExercise: WorkoutExercise) : WorkoutExerciseComponent

    data class Superset(
        val supersetId: String,
        val workoutExercises: List<WorkoutExercise>
    )

}