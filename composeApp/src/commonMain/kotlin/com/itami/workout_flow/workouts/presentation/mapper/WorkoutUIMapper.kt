package com.itami.workout_flow.workouts.presentation.mapper

import com.itami.workout_flow.core.domain.model.workout.Set
import com.itami.workout_flow.core.domain.model.workout.Workout
import com.itami.workout_flow.core.domain.model.workout.WorkoutExercise
import com.itami.workout_flow.workouts.presentation.model.SetUI
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseComponentUI
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseUI
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
            exercises.map { exercise ->
                WorkoutExerciseComponentUI.Single(
                    workoutExercise = exercise.toWorkoutExerciseUI(),
                    expanded = false
                )
            }
        } else {
            listOf(
                WorkoutExerciseComponentUI.Superset(
                    supersetId = supersetId,
                    expanded = false,
                    workoutExercises = exercises
                        .map { it.toWorkoutExerciseUI() }
                        .sortedBy { it.order },
                )
            )
        }
    }.sortedBy { it.order }
}

fun WorkoutExercise.toWorkoutExerciseUI(): WorkoutExerciseUI {
    return WorkoutExerciseUI(
        id = this.id,
        supersetId = this.supersetId,
        exercise = this.exercise,
        sets = this.sets.map { set -> set.toSetUI() },
        order = this.order,
    )
}

fun Set.toSetUI(
): SetUI {
    val hours = this.timeSeconds?.div(3600)
    val minutes = (this.timeSeconds?.rem(3600))?.div(60)
    val seconds = this.timeSeconds?.rem(60)

    return SetUI(
        id = this.id,
        workoutExerciseId = this.workoutExerciseId,
        reps = this.reps,
        weightGrams = this.weightGrams,
        distanceMeters = this.distanceMeters,
        hours = hours,
        minutes = minutes,
        seconds = seconds,
        order = this.order,
    )
}