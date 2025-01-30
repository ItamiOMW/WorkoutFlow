package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalUseFallbackRippleImplementation
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

/**
 * Copy of [androidx.compose.material3.Surface] that supports Brush
 */
@Composable
@NonRestartableComposable
fun GradientSurface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    brush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    contentColor: Color = WorkoutFlowTheme.colors.brandColors.onPrimary,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    val absoluteElevation = LocalAbsoluteTonalElevation.current + tonalElevation
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalAbsoluteTonalElevation provides absoluteElevation
    ) {
        Box(
            modifier = modifier
                .minimumInteractiveComponentSize()
                .surface(
                    shape = shape,
                    backgroundColor = brush,
                    border = border,
                    shadowElevation = with(LocalDensity.current) { shadowElevation.toPx() }
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = rippleOrFallbackImplementation(),
                    enabled = enabled,
                    onClick = onClick
                ),
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}

@Composable
@NonRestartableComposable
fun GradientSurface(
    modifier: Modifier = Modifier,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    brush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    contentColor: Color = WorkoutFlowTheme.colors.brandColors.onPrimary,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    content: @Composable () -> Unit,
) {
    val absoluteElevation = LocalAbsoluteTonalElevation.current + tonalElevation
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalAbsoluteTonalElevation provides absoluteElevation
    ) {
        Box(
            modifier = modifier
                .surface(
                    shape = shape,
                    backgroundColor = brush,
                    border = border,
                    shadowElevation = with(LocalDensity.current) { shadowElevation.toPx() }
                )
                .semantics(mergeDescendants = false) {
                    @Suppress("DEPRECATION")
                    isContainer = true
                }
                .pointerInput(Unit) {},
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}

@Composable
@NonRestartableComposable
fun GradientSurface(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    brush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    contentColor: Color = WorkoutFlowTheme.colors.brandColors.onPrimary,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    val absoluteElevation = LocalAbsoluteTonalElevation.current + tonalElevation
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalAbsoluteTonalElevation provides absoluteElevation
    ) {
        Box(
            modifier =
            modifier
                .minimumInteractiveComponentSize()
                .surface(
                    shape = shape,
                    backgroundColor = brush,
                    border = border,
                    shadowElevation = with(LocalDensity.current) { shadowElevation.toPx() }
                )
                .selectable(
                    selected = selected,
                    interactionSource = interactionSource,
                    indication = rippleOrFallbackImplementation(),
                    enabled = enabled,
                    onClick = onClick
                ),
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}


@Stable
private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Brush,
    border: BorderStroke?,
    shadowElevation: Float,
) = this
    .then(
        if (shadowElevation > 0f) {
            Modifier.graphicsLayer(
                shadowElevation = shadowElevation,
                shape = shape,
                clip = false
            )
        } else {
            Modifier
        }
    )
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(backgroundColor, shape)
    .clip(shape)


@Suppress("DEPRECATION_ERROR")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun rippleOrFallbackImplementation(
    bounded: Boolean = true,
    radius: Dp = Dp.Unspecified,
    color: Color = Color.Unspecified,
): Indication {
    return if (LocalUseFallbackRippleImplementation.current) {
        androidx.compose.material.ripple.rememberRipple(bounded, radius, color)
    } else {
        ripple(bounded, radius, color)
    }
}