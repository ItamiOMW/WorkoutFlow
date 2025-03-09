package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun GradientOutlinedTextButton(
    text: String,
    modifier: Modifier = Modifier,
    outlineBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    containerBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdVertContainer,
    textColor: Color = WorkoutFlowTheme.colors.brandColors.onPrimaryContainer,
    textStyle: TextStyle = WorkoutFlowTheme.typography.labelMedium,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = WorkoutFlowTheme.padding.small,
        vertical = WorkoutFlowTheme.padding.extraSmall
    ),
    onClick: () -> Unit,
) {
    GradientSurface(
        modifier = modifier.semantics { role = Role.Button },
        brush = containerBrush,
        border = BorderStroke(1.dp, outlineBrush),
        shape = shape,
        onClick = onClick,
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(contentPadding)
        )
    }
}