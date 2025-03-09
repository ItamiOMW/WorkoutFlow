package com.itami.workout_flow.core.presentation.utils

import androidx.compose.runtime.Composable
import com.itami.workout_flow.model.WorkoutsSort
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.least_liked
import workoutflow.composeapp.generated.resources.most_liked
import workoutflow.composeapp.generated.resources.newest
import workoutflow.composeapp.generated.resources.oldest

@Composable
fun WorkoutsSort.getName(): String {
    return when (this) {
        WorkoutsSort.Newest -> stringResource(Res.string.newest)
        WorkoutsSort.Oldest -> stringResource(Res.string.oldest)
        WorkoutsSort.MostLiked -> stringResource(Res.string.most_liked)
        WorkoutsSort.LeastLiked -> stringResource(Res.string.least_liked)
    }
}

fun WorkoutsSort.getStringRes(): StringResource {
    return when (this) {
        WorkoutsSort.Newest -> Res.string.newest
        WorkoutsSort.Oldest -> Res.string.oldest
        WorkoutsSort.MostLiked -> Res.string.most_liked
        WorkoutsSort.LeastLiked -> Res.string.least_liked
    }
}