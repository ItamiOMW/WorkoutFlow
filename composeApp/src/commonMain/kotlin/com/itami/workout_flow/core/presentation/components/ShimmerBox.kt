package com.itami.workout_flow.core.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
    shimmerColors: List<Color> = listOf(
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.5f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.7f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 1.0f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.7f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.5f),
    ),
) {
    val transition = rememberInfiniteTransition(label = "Shimmer Loading infinite transition")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer loading animation",
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
        end = Offset(x = translateAnimation.value, y = angleOfAxisY),
    )

    Box(modifier = modifier.background(brush))
}