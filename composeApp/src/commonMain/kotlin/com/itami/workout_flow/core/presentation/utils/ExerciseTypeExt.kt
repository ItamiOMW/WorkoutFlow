package com.itami.workout_flow.core.presentation.utils

import androidx.compose.runtime.Composable
import com.itami.workout_flow.model.ExerciseType
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.exercise_type_distance_and_time
import workoutflow.composeapp.generated.resources.exercise_type_reps
import workoutflow.composeapp.generated.resources.exercise_type_time
import workoutflow.composeapp.generated.resources.exercise_type_weight_and_reps

@Composable
fun ExerciseType.getName(): String {
    return when (this) {
        ExerciseType.WEIGHT_AND_REPS -> stringResource(Res.string.exercise_type_weight_and_reps)
        ExerciseType.REPS -> stringResource(Res.string.exercise_type_reps)
        ExerciseType.DISTANCE_AND_TIME -> stringResource(Res.string.exercise_type_distance_and_time)
        ExerciseType.TIME -> stringResource(Res.string.exercise_type_time)
    }
}