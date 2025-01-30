package com.itami.workout_flow.core.domain.model.workout

sealed class WorkoutExerciseComponent(val order: Int) {

    data class Single(
        val workoutExercise: WorkoutExercise,
    ) : WorkoutExerciseComponent(order = workoutExercise.order)

    data class Superset(
        val supersetId: String,
        val workoutExercises: List<WorkoutExercise>,
    ) : WorkoutExerciseComponent(order = workoutExercises.first().order)

}