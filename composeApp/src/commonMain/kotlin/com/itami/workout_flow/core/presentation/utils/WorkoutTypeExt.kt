package com.itami.workout_flow.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutType
import org.jetbrains.compose.resources.painterResource
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
import workoutflow.composeapp.generated.resources.cardio
import workoutflow.composeapp.generated.resources.endurance
import workoutflow.composeapp.generated.resources.flexibility
import workoutflow.composeapp.generated.resources.hypertrophy
import workoutflow.composeapp.generated.resources.icon_bodybuilder
import workoutflow.composeapp.generated.resources.icon_esg_heart
import workoutflow.composeapp.generated.resources.icon_flexible
import workoutflow.composeapp.generated.resources.icon_run
import workoutflow.composeapp.generated.resources.icon_weightlifting
import workoutflow.composeapp.generated.resources.strength

@Composable
fun WorkoutType.getName(): String {
    return when (this) {
        WorkoutType.Hypertrophy -> stringResource(Res.string.hypertrophy)
        WorkoutType.Strength -> stringResource(Res.string.strength)
        WorkoutType.Cardio -> stringResource(Res.string.cardio)
        WorkoutType.Endurance -> stringResource(Res.string.endurance)
        WorkoutType.Flexibility -> stringResource(Res.string.flexibility)
    }
}

@Composable
fun WorkoutType.getIconPainter(): Painter {
    return when (this) {
        WorkoutType.Hypertrophy -> painterResource(Res.drawable.icon_bodybuilder)
        WorkoutType.Strength -> painterResource(Res.drawable.icon_weightlifting)
        WorkoutType.Cardio -> painterResource(Res.drawable.icon_esg_heart)
        WorkoutType.Endurance -> painterResource(Res.drawable.icon_run)
        WorkoutType.Flexibility -> painterResource(Res.drawable.icon_flexible)
    }
}