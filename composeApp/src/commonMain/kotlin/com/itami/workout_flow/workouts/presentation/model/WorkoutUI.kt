package com.itami.workout_flow.workouts.presentation.model

import com.itami.workout_flow.core.domain.model.user.UserProfile
import com.itami.workout_flow.core.domain.model.workout.WorkoutExercise
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutType
import kotlinx.datetime.Instant

data class WorkoutUI(
    val id: String,
    val author: UserProfile?,
    val name: String,
    val description: String,
    val durationMin: Int,
    val workoutTypes: List<WorkoutType>,
    val equipments: List<Equipment>,
    val muscles: List<Muscle>,
    val workoutExercises: List<WorkoutExerciseComponentUI>,
    val favoritesCount: Int,
    val isFavorite: Boolean,
    val isPublic: Boolean,
    val isCreatedByCurrentUser: Boolean,
    val createdAt: Instant,
)

sealed class WorkoutExerciseComponentUI(
    val exerciseId: String,
    val order: Int,
    val totalSetsCount: Int,
    open val expanded: Boolean,
) {

    data class Single(
        val workoutExercise: WorkoutExerciseUI,
        override val expanded: Boolean,
    ) : WorkoutExerciseComponentUI(
        exerciseId = workoutExercise.id,
        order = workoutExercise.order,
        totalSetsCount = workoutExercise.sets.size,
        expanded = expanded,
    )

    data class Superset(
        val supersetId: String,
        val workoutExercises: List<WorkoutExerciseUI>,
        override val expanded: Boolean,
    ) : WorkoutExerciseComponentUI(
        exerciseId = workoutExercises.first().id,
        order = workoutExercises.first().order,
        totalSetsCount = workoutExercises.flatMap { it.sets }.size,
        expanded = expanded,
    )

    fun copy(
        order: Int = this.order,
        expanded: Boolean = this.expanded
    ): WorkoutExerciseComponentUI {
        return when(this) {
            is Single -> copy(expanded = expanded, order = order)
            is Superset -> copy(expanded = expanded, order = order)
        }
    }

}

data class WorkoutExerciseUI(
    val id: String,
    val supersetId: String?,
    val exercise: Exercise,
    val sets: List<SetUI>,
    val order: Int,
)

data class SetUI(
    val id: String,
    val workoutExerciseId: String,
    val reps: Int?,
    val weightGrams: Float?,
    val distanceMeters: Float?,
    val hours: Int?,
    val minutes: Int?,
    val seconds: Int?,
    val order: Int,
)