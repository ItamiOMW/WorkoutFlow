package com.itami.workout_flow.core.presentation.utils

import androidx.compose.runtime.Composable
import com.itami.workout_flow.model.Equipment
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
fun Equipment.getName(): String {
    return when (this) {
        Equipment.Bodyweight -> stringResource(Res.string.chest)
        Equipment.Calisthenics -> TODO()
        Equipment.Barbell -> TODO()
        Equipment.Dumbbells -> TODO()
        Equipment.Machines -> TODO()
        Equipment.Bands -> TODO()
    }
}