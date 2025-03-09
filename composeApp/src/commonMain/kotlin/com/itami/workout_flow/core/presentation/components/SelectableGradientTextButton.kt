package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun SelectableGradientTextButton(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    outlineColor: Color = WorkoutFlowTheme.colors.outlineColors.outlineHigh,
    containerBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdVert,
    selectedTextColor: Color = WorkoutFlowTheme.colors.brandColors.onPrimary,
    unselectedTextColor: Color = WorkoutFlowTheme.colors.backgroundColors.onBackground,
    textStyle: TextStyle = WorkoutFlowTheme.typography.labelMedium,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    onClick: () -> Unit,
) {
    GradientSurface(
        modifier = modifier,
        brush = if (selected) {
            containerBrush
        } else {
            Brush.linearGradient(colors = listOf(Color.Transparent, Color.Transparent))
        },
        border = if (!selected) BorderStroke(1.dp, outlineColor) else null,
        shape = shape,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = textStyle,
            color = if (selected) selectedTextColor else unselectedTextColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = WorkoutFlowTheme.padding.extraSmall)
        )
    }
}