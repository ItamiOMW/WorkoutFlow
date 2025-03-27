package com.itami.workout_flow.workouts.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun ShimmerExerciseItem(
    modifier: Modifier = Modifier,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
    containerColor: Color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
    shimmerColors: List<Color> = listOf(
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.1f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.2f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.3f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.2f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.1f),
    ),
    shape: Shape = WorkoutFlowTheme.shapes.small,
    shadowElevation: Dp = 3.dp,
) {
    val transition = rememberInfiniteTransition(label = "Shimmer infinite transition")

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

    Surface(
        modifier = modifier,
        color = containerColor,
        shape = shape,
        shadowElevation = shadowElevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WorkoutFlowTheme.padding.default),
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(
                modifier = Modifier
                    .size(64.dp)
                    .clip(WorkoutFlowTheme.shapes.small)
                    .background(brush),
            )
            Spacer(modifier = Modifier.width(WorkoutFlowTheme.padding.small))
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(48.dp)
                        .height(14.dp)
                        .clip(WorkoutFlowTheme.shapes.large)
                        .background(brush)
                )
                Spacer(
                    modifier = Modifier
                        .width(80.dp)
                        .height(12.dp)
                        .clip(WorkoutFlowTheme.shapes.large)
                        .background(brush)
                )
                Spacer(
                    modifier = Modifier
                        .width(32.dp)
                        .height(12.dp)
                        .clip(WorkoutFlowTheme.shapes.large)
                        .background(brush)
                )
            }
        }
    }
}