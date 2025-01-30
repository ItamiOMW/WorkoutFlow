package com.itami.workout_flow.home.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.components.IconToText
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.core.presentation.utils.getIconPainter
import com.itami.workout_flow.core.presentation.utils.getName
import com.itami.workout_flow.model.WorkoutType
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.workout_types

@Composable
internal fun HomeWorkoutTypesSection(
    modifier: Modifier = Modifier,
    types: List<WorkoutType>,
    onWorkoutTypeClick: (WorkoutType) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.medium)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = WorkoutFlowTheme.padding.default),
            text = stringResource(Res.string.workout_types),
            style = WorkoutFlowTheme.typography.labelLarge
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.default),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = WorkoutFlowTheme.padding.default)
        ) {
            items(items = types, key = { it.name }) { type ->
                Surface(
                    onClick = { onWorkoutTypeClick(type) },
                    color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
                    contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                    shadowElevation = 3.dp,
                    shape = WorkoutFlowTheme.shapes.extraLarge
                ) {
                    IconToText(
                        modifier = Modifier.padding(
                            horizontal = WorkoutFlowTheme.padding.small,
                            vertical = WorkoutFlowTheme.padding.extraSmall
                        ),
                        iconPainter = type.getIconPainter(),
                        iconBrush = WorkoutFlowTheme.colors.brandColors.primaryGdVert,
                        iconSize = 24.dp,
                        text = type.getName(),
                        textStyle = WorkoutFlowTheme.typography.labelMedium,
                        textColor = WorkoutFlowTheme.colors.surfaceColors.onSurface
                    )
                }
            }
        }
    }
}