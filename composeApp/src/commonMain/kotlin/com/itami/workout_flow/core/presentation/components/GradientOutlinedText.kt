package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun GradientOutlinedText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = WorkoutFlowTheme.typography.labelSmall,
    textColor: Color = WorkoutFlowTheme.colors.brandColors.onPrimaryContainer,
    containerBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdVertContainer,
    borderBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    borderWidth: Dp = 1.dp,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = WorkoutFlowTheme.padding.small,
        vertical = WorkoutFlowTheme.padding.extraSmall
    ),
    shape: Shape = WorkoutFlowTheme.shapes.extraLarge,
) {
    GradientSurface(
        modifier = modifier,
        brush = containerBrush,
        contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
        shape = shape,
        border = BorderStroke(width = borderWidth, brush = borderBrush)
    ) {
        Box(
            modifier = Modifier.padding(contentPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = textStyle,
                textAlign = TextAlign.Center,
                color = textColor
            )
        }
    }
}

@Composable
fun GradientOutlinedText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = WorkoutFlowTheme.typography.labelSmall,
    textColor: Color = WorkoutFlowTheme.colors.brandColors.onPrimaryContainer,
    containerBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdVertContainer,
    borderBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    borderWidth: Dp = 1.dp,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = WorkoutFlowTheme.padding.small,
        vertical = WorkoutFlowTheme.padding.extraSmall
    ),
    shape: Shape = WorkoutFlowTheme.shapes.extraLarge,
    onClick: () -> Unit,
) {
    GradientSurface(
        modifier = modifier,
        brush = containerBrush,
        contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
        shape = shape,
        border = BorderStroke(width = borderWidth, brush = borderBrush),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.padding(contentPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = textStyle,
                textAlign = TextAlign.Center,
                color = textColor
            )
        }
    }
}