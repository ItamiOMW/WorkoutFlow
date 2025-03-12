package com.itami.workout_flow.workouts.presentation.mapper

import com.itami.workout_flow.core.domain.model.workout.Workout
import com.itami.workout_flow.core.domain.model.workout.WorkoutExercise
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseComponentUI
import com.itami.workout_flow.workouts.presentation.model.WorkoutUI

fun Workout.toWorkoutUI() = WorkoutUI(
    id = this.id,
    author = this.author,
    name = this.name,
    description = this.description,
    durationMin = this.durationMin,
    workoutTypes = this.workoutTypes,
    equipments = this.equipments,
    muscles = this.muscles,
    workoutExercises = this.workoutExercises.toWorkoutExerciseComponentsUI(),
    favoritesCount = this.favoritesCount,
    isFavorite = this.isFavorite,
    isPublic = this.isPublic,
    isCreatedByCurrentUser = this.isCreatedByCurrentUser,
    createdAt = this.createdAt
)

fun List<WorkoutExercise>.toWorkoutExerciseComponentsUI(): List<WorkoutExerciseComponentUI> {
    val groupedBySuperset = this.groupBy { it.supersetId }

    return groupedBySuperset.flatMap { (supersetId, exercises) ->
        if (supersetId == null) {
            exercises.map { WorkoutExerciseComponentUI.Single(it, expanded = false) }
        } else {
            listOf(
                WorkoutExerciseComponentUI.Superset(
                    supersetId = supersetId,
                    workoutExercises = exercises.sortedBy { it.order },
                    expanded = false
                )
            )
        }
    }.sortedBy { it.order }
}