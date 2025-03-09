package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.check_icon
import workoutflow.composeapp.generated.resources.icon_check

@Composable
fun GradientCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkboxSize: Dp = 40.dp,
    checkmarkSize: Dp = 24.dp,
    checkedBoxBrush: Brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz,
    uncheckedBoxColor: Color = Color.Transparent,
    uncheckedBorderColor: Color = WorkoutFlowTheme.colors.outlineColors.outlineHigh,
    checkmarkColor: Color = WorkoutFlowTheme.colors.brandColors.onPrimary,
    boxShape: Shape = WorkoutFlowTheme.shapes.extraSmall
) {
    Box(
        modifier = Modifier
            .size(checkboxSize)
            .then(
                if (checked) Modifier.background(brush = checkedBoxBrush, shape = boxShape)
                else Modifier
                    .background(color = uncheckedBoxColor, shape = boxShape)
                    .border(1.dp, uncheckedBorderColor)
            )
            .clickable { onCheckedChange(!checked) }
            .semantics { Role.Checkbox },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                painter = painterResource(Res.drawable.icon_check),
                contentDescription = stringResource(Res.string.check_icon),
                tint = checkmarkColor,
                modifier = Modifier.size(checkmarkSize),
            )
        }
    }
}