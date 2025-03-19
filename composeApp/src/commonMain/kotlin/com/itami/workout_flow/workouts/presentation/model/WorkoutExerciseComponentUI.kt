package com.itami.workout_flow.workouts.presentation.model

sealed class WorkoutExerciseComponentUI(
    val workoutExerciseId: String,
    val totalSetsCount: Int,
    open val order: Int,
    open val expanded: Boolean,
) {

    data class Single(
        val workoutExercise: WorkoutExerciseUI,
        override val order: Int = workoutExercise.order,
        override val expanded: Boolean = false,
    ) : WorkoutExerciseComponentUI(
        workoutExerciseId = workoutExercise.id,
        totalSetsCount = workoutExercise.sets.size,
        order = order,
        expanded = expanded,
    )

    data class Superset(
        val supersetId: String,
        val workoutExercises: List<WorkoutExerciseUI>,
        override val order: Int = workoutExercises.first().order,
        override val expanded: Boolean = false,
    ) : WorkoutExerciseComponentUI(
        workoutExerciseId = workoutExercises.first().id,
        totalSetsCount = workoutExercises.flatMap { it.sets }.size,
        order = order,
        expanded = expanded,
    )

    fun copyComponent(
        order: Int = this.order,
        expanded: Boolean = this.expanded
    ): WorkoutExerciseComponentUI {
        return when(this) {
            is Single -> this.copy(expanded = expanded, order = order)
            is Superset -> this.copy(expanded = expanded, order = order)
        }
    }

}