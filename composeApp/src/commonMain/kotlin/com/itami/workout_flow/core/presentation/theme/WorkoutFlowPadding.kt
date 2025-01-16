package com.itami.workout_flow.core.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class WorkoutFlowPadding(
    val none: Dp = 0.dp,
    val tiny: Dp =  4.dp,
    val extraSmall: Dp = 8.dp,
    val small: Dp = 12.dp,
    val default: Dp = 16.dp,
    val medium: Dp = 20.dp,
    val large: Dp = 36.dp,
    val extraLarge: Dp = 48.dp,
)