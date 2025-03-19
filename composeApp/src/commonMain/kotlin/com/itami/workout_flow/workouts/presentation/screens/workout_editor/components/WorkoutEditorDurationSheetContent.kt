package com.itami.workout_flow.workouts.presentation.screens.workout_editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.components.GradientOutlinedText
import com.itami.workout_flow.core.presentation.components.button.GradientButton
import com.itami.workout_flow.core.presentation.components.button.GradientButtonColors
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.apply
import workoutflow.composeapp.generated.resources.decrease_duration
import workoutflow.composeapp.generated.resources.duration
import workoutflow.composeapp.generated.resources.duration_min
import workoutflow.composeapp.generated.resources.icon_add
import workoutflow.composeapp.generated.resources.icon_remove
import workoutflow.composeapp.generated.resources.increase_duration

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun WorkoutEditorDurationSheetContent(
    modifier: Modifier = Modifier,
    initialDurationMin: Int = 0,
    durationOptions: List<Int> = listOf(10, 20, 30, 45, 60, 90, 120),
    onApply: (durationMin: Int) -> Unit,
) {
    var durationMinState by remember { mutableIntStateOf(initialDurationMin) }

    val decreaseDuration = {
        durationMinState = (durationMinState - 1).coerceAtLeast(0)
    }

    val increaseDuration = {
        durationMinState = (durationMinState + 1).coerceAtMost(120)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.duration),
            style = WorkoutFlowTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(WorkoutFlowTheme.padding.large))
        Row(
            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.default),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GradientButton(
                onClick = decreaseDuration,
                shape = CircleShape,
                elevation = 0.dp,
                border = BorderStroke(
                    width = 1.dp,
                    brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz
                ),
                contentPadding = PaddingValues(WorkoutFlowTheme.padding.extraSmall),
                colors = GradientButtonColors(
                    containerBrush = WorkoutFlowTheme.colors.brandColors.primaryGdVertContainer,
                    contentColor = WorkoutFlowTheme.colors.brandColors.onPrimaryContainer,
                ),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_remove),
                    contentDescription = stringResource(Res.string.decrease_duration),
                    modifier = Modifier.size(16.dp),
                )
            }
            GradientOutlinedText(
                modifier = Modifier,
                text = stringResource(Res.string.duration_min, durationMinState),
                textStyle = WorkoutFlowTheme.typography.titleSmall,
                borderWidth = 2.dp,
                borderBrush = WorkoutFlowTheme.colors.brandColors.primaryGdVert,
                containerBrush = Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)),
                contentPadding = PaddingValues(
                    horizontal = WorkoutFlowTheme.padding.medium,
                    vertical = WorkoutFlowTheme.padding.default
                ),
                shape = WorkoutFlowTheme.shapes.small
            )
            GradientButton(
                onClick = increaseDuration,
                shape = CircleShape,
                elevation = 0.dp,
                border = BorderStroke(
                    width = 1.dp,
                    brush = WorkoutFlowTheme.colors.brandColors.primaryGdHoriz
                ),
                contentPadding = PaddingValues(WorkoutFlowTheme.padding.extraSmall),
                colors = GradientButtonColors(
                    containerBrush = WorkoutFlowTheme.colors.brandColors.primaryGdVertContainer,
                    contentColor = WorkoutFlowTheme.colors.brandColors.onPrimaryContainer,
                ),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_add),
                    contentDescription = stringResource(Res.string.increase_duration),
                    modifier = Modifier.size(16.dp),
                )
            }
        }
        Spacer(Modifier.height(WorkoutFlowTheme.padding.extraLarge))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.default),
            maxItemsInEachRow = 4,
        ) {
            durationOptions.forEachIndexed { index, durationMin ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    GradientOutlinedText(
                        modifier = Modifier,
                        text = stringResource(Res.string.duration_min, durationMin),
                        textStyle = WorkoutFlowTheme.typography.labelMedium,
                        onClick = {
                            durationMinState = durationMin
                        }
                    )
                    if (index < durationOptions.size - 1) {
                        Spacer(Modifier.width(WorkoutFlowTheme.padding.default))
                    }
                }
            }
        }
        Spacer(Modifier.height(WorkoutFlowTheme.padding.extraLarge))
        GradientButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onApply(
                    durationMinState.coerceIn(
                        0..120
                    )
                )
            },
        ) {
            Text(
                text = stringResource(Res.string.apply),
                style = WorkoutFlowTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}