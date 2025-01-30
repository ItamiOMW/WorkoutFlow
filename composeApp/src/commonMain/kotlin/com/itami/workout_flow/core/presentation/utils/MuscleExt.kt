package com.itami.workout_flow.core.presentation.utils

import androidx.compose.runtime.Composable
import com.itami.workout_flow.model.Muscle
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.biceps
import workoutflow.composeapp.generated.resources.chest
import workoutflow.composeapp.generated.resources.core
import workoutflow.composeapp.generated.resources.forearms
import workoutflow.composeapp.generated.resources.front_delt
import workoutflow.composeapp.generated.resources.fullbody
import workoutflow.composeapp.generated.resources.glutes
import workoutflow.composeapp.generated.resources.hamstrings
import workoutflow.composeapp.generated.resources.lats
import workoutflow.composeapp.generated.resources.lower_traps
import workoutflow.composeapp.generated.resources.middle_delt
import workoutflow.composeapp.generated.resources.quadriceps
import workoutflow.composeapp.generated.resources.rear_delt
import workoutflow.composeapp.generated.resources.triceps
import workoutflow.composeapp.generated.resources.upper_traps
import workoutflow.composeapp.generated.resources.neck
import workoutflow.composeapp.generated.resources.calves

@Composable
fun List<Muscle>.joinMuscleNames(): String {
    val muscleNames = map { it.getName() }
    return muscleNames.joinToString()
}

@Composable
fun Muscle.getName(): String {
    return when (this) {
        Muscle.Chest -> stringResource(Res.string.chest)
        Muscle.FrontDelt -> stringResource(Res.string.front_delt)
        Muscle.MiddleDelt -> stringResource(Res.string.middle_delt)
        Muscle.RearDelt -> stringResource(Res.string.rear_delt)
        Muscle.Lats -> stringResource(Res.string.lats)
        Muscle.UpperTraps -> stringResource(Res.string.upper_traps)
        Muscle.LowerTraps -> stringResource(Res.string.lower_traps)
        Muscle.Triceps -> stringResource(Res.string.triceps)
        Muscle.Biceps -> stringResource(Res.string.biceps)
        Muscle.Forearms -> stringResource(Res.string.forearms)
        Muscle.Quadriceps -> stringResource(Res.string.quadriceps)
        Muscle.Hamstrings -> stringResource(Res.string.hamstrings)
        Muscle.Glutes -> stringResource(Res.string.glutes)
        Muscle.Calves -> stringResource(Res.string.calves)
        Muscle.Neck -> stringResource(Res.string.neck)
        Muscle.Core -> stringResource(Res.string.core)
        Muscle.Fullbody -> stringResource(Res.string.fullbody)
    }
}