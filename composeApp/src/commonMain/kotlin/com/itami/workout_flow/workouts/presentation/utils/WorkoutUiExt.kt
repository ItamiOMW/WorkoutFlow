package com.itami.workout_flow.workouts.presentation.utils

import com.itami.workout_flow.workouts.presentation.model.SetUI
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseComponentUI
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseUI

fun List<WorkoutExerciseComponentUI>.getTotalWorkoutExercisesSize(): Int {
    val workoutExercises = this.flatMap {
        when (it) {
            is WorkoutExerciseComponentUI.Single -> {
                listOf(it.workoutExercise)
            }

            is WorkoutExerciseComponentUI.Superset -> {
                it.workoutExercises
            }
        }
    }
    return workoutExercises.size
}

fun List<WorkoutExerciseComponentUI>.reassignOrder(): List<WorkoutExerciseComponentUI> =
    mapIndexed { index, component ->
        component.copyComponent(order = index + 1)
    }

fun List<WorkoutExerciseUI>.reassignOrder(): List<WorkoutExerciseUI> =
    mapIndexed { index, workoutExercise -> workoutExercise.copy(order = index + 1) }

fun List<SetUI>.reassignOrder(): List<SetUI> {
    return mapIndexed { index, set -> set.copy(order = index + 1) }
}