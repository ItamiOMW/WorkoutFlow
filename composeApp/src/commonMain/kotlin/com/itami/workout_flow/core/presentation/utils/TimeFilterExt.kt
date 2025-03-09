package com.itami.workout_flow.core.presentation.utils

import androidx.compose.runtime.Composable
import com.itami.workout_flow.model.WorkoutTimeFilter
import com.itami.workout_flow.model.WorkoutsSort
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.between_30_and_60min
import workoutflow.composeapp.generated.resources.between_60_and_90min
import workoutflow.composeapp.generated.resources.least_liked
import workoutflow.composeapp.generated.resources.less_than_30min
import workoutflow.composeapp.generated.resources.more_than_90min
import workoutflow.composeapp.generated.resources.most_liked
import workoutflow.composeapp.generated.resources.newest
import workoutflow.composeapp.generated.resources.oldest

@Composable
fun WorkoutTimeFilter.getName(): String {
    return when (this) {
        WorkoutTimeFilter.LessThan30Min -> stringResource(Res.string.less_than_30min)
        WorkoutTimeFilter.Between30And60Min -> stringResource(Res.string.between_30_and_60min)
        WorkoutTimeFilter.Between60And90Min -> stringResource(Res.string.between_60_and_90min)
        WorkoutTimeFilter.MoreThan90Min -> stringResource(Res.string.more_than_90min)
    }
}