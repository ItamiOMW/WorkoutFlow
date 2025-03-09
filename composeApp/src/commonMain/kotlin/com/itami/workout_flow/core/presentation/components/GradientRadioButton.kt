package com.itami.workout_flow.core.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

private const val RadioAnimationDuration = 100

private val RadioButtonPadding = 2.dp
private val RadioButtonDotSize = 12.dp
private val RadioStrokeWidth = 2.dp

/**
 * Copy of [androidx.compose.material3.RadioButton] that supports Brush
 */
@Composable
fun GradientRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    unselectedColor: Color = WorkoutFlowTheme.colors.outlineColors.outlineHigh,
    interactionSource: MutableInteractionSource? = null,
) {
    val dotRadius = animateDpAsState(
        targetValue = if (selected) RadioButtonDotSize / 2 else 0.dp,
        animationSpec = tween(durationMillis = RadioAnimationDuration),
        label = "dotRadius"
    )

    val selectableModifier =
        if (onClick != null) {
            Modifier.selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = rippleOrFallbackImplementation(
                    bounded = false,
                    radius = 20.dp
                )
            )
        } else {
            Modifier
        }
    Canvas(
        modifier
            .then(
                if (onClick != null) {
                    Modifier.minimumInteractiveComponentSize()
                } else {
                    Modifier
                }
            )
            .then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(RadioButtonPadding)
            .requiredSize(20.dp)
    ) {
        val strokeWidth = RadioStrokeWidth.toPx()

        if (selected) {
            drawCircle(
                brush = selectedBrush,
                radius = (10.dp).toPx() - strokeWidth / 2,
                style = Stroke(strokeWidth)
            )
        } else {
            drawCircle(
                color = unselectedColor,
                radius = (10.dp).toPx() - strokeWidth / 2,
                style = Stroke(strokeWidth)
            )
        }

        if (dotRadius.value > 0.dp) {
            if (selected) {
                drawCircle(selectedBrush, dotRadius.value.toPx() - strokeWidth / 2, style = Fill)
            } else {
                drawCircle(unselectedColor, dotRadius.value.toPx() - strokeWidth / 2, style = Fill)
            }
        }
    }
}