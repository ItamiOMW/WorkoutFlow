package com.itami.workout_flow.core.presentation.utils

import androidx.compose.runtime.Composable
import com.itami.workout_flow.core.domain.model.workout.DistanceUnit
import com.itami.workout_flow.core.domain.model.workout.WeightUnit
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.kilometers_short
import workoutflow.composeapp.generated.resources.miles_short

@Composable
fun DistanceUnit.getShortName(): String {
    return when(this) {
        DistanceUnit.KILOMETERS -> stringResource(Res.string.kilometers_short)
        DistanceUnit.MILES -> stringResource(Res.string.miles_short)
    }
}