package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun RadioButtonToText(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    textStyle: TextStyle = WorkoutFlowTheme.typography.labelMedium,
    textColor: Color = WorkoutFlowTheme.colors.backgroundColors.onBackground,
    selectedBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    unselectedColor: Color = WorkoutFlowTheme.colors.outlineColors.outlineHigh,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
    onRadioButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GradientRadioButton(
            selectedBrush = selectedBrush,
            unselectedColor = unselectedColor,
            selected = selected,
            onClick = onRadioButtonClick,
        )
        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}