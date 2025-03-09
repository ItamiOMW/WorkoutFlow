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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun CheckboxToText(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    checkboxSize: Dp = 40.dp,
    checkmarkSize: Dp = 24.dp,
    textStyle: TextStyle = WorkoutFlowTheme.typography.labelSmall,
    textColor: Color = WorkoutFlowTheme.colors.backgroundColors.onBackground,
    checkmarkColor: Color = WorkoutFlowTheme.colors.brandColors.onPrimary,
    checkedBoxBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    uncheckedBoxColor: Color = Color.Transparent,
    uncheckedBorderColor: Color = WorkoutFlowTheme.colors.outlineColors.outlineHigh,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
    onCheckedChange: (checked: Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GradientCheckbox(
            checked = checked,
            checkboxSize = checkboxSize,
            checkmarkSize = checkmarkSize,
            checkmarkColor = checkmarkColor,
            checkedBoxBrush = checkedBoxBrush,
            uncheckedBoxColor = uncheckedBoxColor,
            uncheckedBorderColor = uncheckedBorderColor,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}