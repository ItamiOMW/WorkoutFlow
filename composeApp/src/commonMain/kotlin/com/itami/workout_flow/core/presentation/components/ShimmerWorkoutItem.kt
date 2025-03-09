package com.itami.workout_flow.core.presentation.components

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
import androidx.compose.foundation.shape.CircleShape
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
fun ShimmerWorkoutItem(
    modifier: Modifier = Modifier,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
    containerColor: Color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
    shimmerColors: List<Color> = listOf(
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.5f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.7f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 1.0f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.7f),
        WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.5f),
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WorkoutFlowTheme.padding.default),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(brush),
                    )
                    Spacer(modifier = Modifier.width(WorkoutFlowTheme.padding.extraSmall))
                    Spacer(
                        modifier = Modifier
                            .height(14.dp)
                            .width(64.dp)
                            .clip(WorkoutFlowTheme.shapes.large)
                            .background(brush),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(brush),
                )
            }
            Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.small))
            Spacer(
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth(0.5f)
                    .clip(WorkoutFlowTheme.shapes.large)
                    .background(brush),
            )
            Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.small))
            Row(horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall)) {
                Spacer(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(brush),
                )
                Spacer(
                    modifier = Modifier
                        .height(14.dp)
                        .fillMaxWidth(0.7f)
                        .clip(WorkoutFlowTheme.shapes.large)
                        .background(brush),
                )
            }
            Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.small))
            Row(
                horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(brush),
                    )
                    Spacer(
                        modifier = Modifier
                            .height(14.dp)
                            .width(32.dp)
                            .clip(WorkoutFlowTheme.shapes.large)
                            .background(brush),
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(brush),
                    )
                    Spacer(
                        modifier = Modifier
                            .height(14.dp)
                            .width(32.dp)
                            .clip(WorkoutFlowTheme.shapes.large)
                            .background(brush),
                    )
                }
            }
        }
    }
}