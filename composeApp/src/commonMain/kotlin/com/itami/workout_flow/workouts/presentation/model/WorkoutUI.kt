package com.itami.workout_flow.workouts.presentation.model

import com.itami.workout_flow.core.domain.model.user.UserProfile
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