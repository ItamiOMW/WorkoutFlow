package com.itami.workout_flow.core.presentation.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.components.GradientSurface
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

/**
 * Copy of [androidx.compose.material3.Button] that supports Brush
 */
@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    colors: GradientButtonColors = GradientButtonColors(
        containerBrush = WorkoutFlowTheme.colors.brandColors.primaryGdVert,
        contentColor = WorkoutFlowTheme.colors.brandColors.onPrimary,
        disabledContainerBrush = Brush.verticalGradient(colors = listOf(WorkoutFlowTheme.colors.surfaceColors.surfaceHigh)),
        disabledContentColor = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant
    ),
    elevation: Dp = 3.dp,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = WorkoutFlowTheme.padding.default,
        vertical = WorkoutFlowTheme.padding.small
    ),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val containerBrush = colors.containerColor(enabled)
    val contentColor = colors.contentColor(enabled)

    GradientSurface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        brush = containerBrush,
        contentColor = contentColor,
        shadowElevation = elevation,
        border = border,
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}