package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme

@Composable
fun DialogComponent(
    title: String,
    description: String,
    cancelText: String,
    confirmText: String,
    modifier: Modifier = Modifier,
    containerColor: Color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    tonalElevation: Dp = 5.dp,
    contentPadding: PaddingValues = PaddingValues(
        start =  WorkoutFlowTheme.padding.default,
        top =  WorkoutFlowTheme.padding.default,
        end =  WorkoutFlowTheme.padding.default,
        bottom =  WorkoutFlowTheme.padding.extraSmall
    ),
    titleStyle: TextStyle = WorkoutFlowTheme.typography.titleSmall,
    descriptionStyle: TextStyle = WorkoutFlowTheme.typography.bodyLarge,
    confirmTextStyle: TextStyle = WorkoutFlowTheme.typography.labelSmall,
    cancelTextStyle: TextStyle = WorkoutFlowTheme.typography.labelSmall,
    titleColor: Color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
    descriptionColor: Color = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = containerColor,
            tonalElevation = tonalElevation,
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = titleStyle,
                    color = titleColor,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.default))
                Text(
                    text = description,
                    style = descriptionStyle,
                    color = descriptionColor,
                    textAlign = TextAlign.Center,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.medium))
                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GradientOutlinedTextButton(
                        modifier = Modifier,
                        text = cancelText,
                        textStyle = cancelTextStyle,
                        onClick = onDismiss,
                    )
                    GradientOutlinedTextButton(
                        modifier = Modifier,
                        text = confirmText,
                        textStyle = confirmTextStyle,
                        onClick = onConfirm,
                    )
                }
            }
        }
    }
}