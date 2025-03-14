package com.itami.workout_flow.core.presentation.utils

import androidx.compose.runtime.Composable
import com.itami.workout_flow.core.domain.model.workout.WeightUnit
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.kilograms_short
import workoutflow.composeapp.generated.resources.pounds_short

@Composable
fun WeightUnit.getShortName(): String {
    return when(this) {
        WeightUnit.KILOGRAMS -> stringResource(Res.string.kilograms_short)
        WeightUnit.POUNDS -> stringResource(Res.string.pounds_short)
    }
}