package com.itami.workout_flow.workouts.presentation.screens.workouts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.itami.workout_flow.core.presentation.components.RadioButtonToText
import com.itami.workout_flow.core.presentation.components.button.GradientButton
import com.itami.workout_flow.core.presentation.components.button.GradientButtonColors
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.core.presentation.utils.getStringRes
import com.itami.workout_flow.model.WorkoutsSort
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.apply
import workoutflow.composeapp.generated.resources.reset
import workoutflow.composeapp.generated.resources.sort_by

@Composable
internal fun WorkoutsScreenSortBottomSheetContent(
    modifier: Modifier = Modifier,
    selectedWorkoutSort: WorkoutsSort = WorkoutsSort.Newest,
    onApplyClick: (workoutSort: WorkoutsSort) -> Unit,
) {
    var workoutSortState by remember { mutableStateOf(selectedWorkoutSort) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = WorkoutFlowTheme.padding.default),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(Res.string.sort_by),
                style = WorkoutFlowTheme.typography.labelLarge,
            )
            Spacer(Modifier.height(WorkoutFlowTheme.padding.medium))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall)
            ) {
                WorkoutsSort.entries.forEach { workoutSort ->
                    RadioButtonToText(
                        text = stringResource(workoutSort.getStringRes()),
                        selected = workoutSort == workoutSortState,
                        textColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        onRadioButtonClick = { workoutSortState = workoutSort }
                    )
                }
            }
            Spacer(Modifier.height(WorkoutFlowTheme.padding.extraLarge))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small)
            ) {
                GradientButton(
                    modifier = Modifier.weight(1f),
                    onClick = { workoutSortState = WorkoutsSort.Newest },
                    colors = GradientButtonColors(
                        containerBrush = WorkoutFlowTheme.colors.brandColors.primaryGdVertContainer,
                        contentColor = WorkoutFlowTheme.colors.brandColors.onPrimaryContainer
                    ),
                    contentPadding = PaddingValues(vertical = WorkoutFlowTheme.padding.small)
                ) {
                    Text(
                        text = stringResource(Res.string.reset),
                        style = WorkoutFlowTheme.typography.labelLarge
                    )
                }
                GradientButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onApplyClick(workoutSortState) },
                    colors = GradientButtonColors(
                        containerBrush = WorkoutFlowTheme.colors.brandColors.primaryGdVert,
                        contentColor = WorkoutFlowTheme.colors.brandColors.onPrimary
                    ),
                    contentPadding = PaddingValues(vertical = WorkoutFlowTheme.padding.small)
                ) {
                    Text(
                        text = stringResource(Res.string.apply),
                        style = WorkoutFlowTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}