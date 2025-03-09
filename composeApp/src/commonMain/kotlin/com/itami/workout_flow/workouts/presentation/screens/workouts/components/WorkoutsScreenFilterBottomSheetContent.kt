package com.itami.workout_flow.workouts.presentation.screens.workouts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.components.CheckboxToText
import com.itami.workout_flow.core.presentation.components.SelectableGradientTextButton
import com.itami.workout_flow.core.presentation.components.button.GradientButton
import com.itami.workout_flow.core.presentation.components.button.GradientButtonColors
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.core.presentation.utils.calculateGridHeight
import com.itami.workout_flow.core.presentation.utils.getName
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutTimeFilter
import com.itami.workout_flow.model.WorkoutType
import com.itami.workout_flow.model.WorkoutsFilter
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.apply
import workoutflow.composeapp.generated.resources.duration
import workoutflow.composeapp.generated.resources.equipment
import workoutflow.composeapp.generated.resources.reset
import workoutflow.composeapp.generated.resources.targeted_muscles
import workoutflow.composeapp.generated.resources.workout_types

private const val DEFAULT_CHECKBOX_TO_TEXT_HEIGHT = 20
private const val DEFAULT_GRID_COLUMNS_COUNT = 3
private const val DEFAULT_GRID_CONTENT_PADDING = 16

@Composable
internal fun WorkoutsScreenFilterBottomSheetContent(
    modifier: Modifier = Modifier,
    workoutsFilter: WorkoutsFilter = WorkoutsFilter(),
    onApplyClick: (workoutsFilter: WorkoutsFilter) -> Unit,
) {
    val selectedMuscles = remember { mutableStateListOf(*workoutsFilter.selectedMuscles.toTypedArray()) }
    val selectedEquipments = remember { mutableStateListOf(*workoutsFilter.selectedEquipments.toTypedArray()) }
    val selectedWorkoutTypes = remember { mutableStateListOf(*workoutsFilter.selectedWorkoutTypes.toTypedArray()) }
    val selectedWorkoutTimeFilters = remember { mutableStateListOf(*workoutsFilter.selectedTimeFilters.toTypedArray()) }

    val scrollState = rememberScrollState()

    val workoutTypesGridHeight = remember(WorkoutType.entries.size) {
        calculateGridHeight(
            itemCount = WorkoutType.entries.size,
            itemHeight = DEFAULT_CHECKBOX_TO_TEXT_HEIGHT,
            gridColumnCount = DEFAULT_GRID_COLUMNS_COUNT,
            verticalSpacing = DEFAULT_GRID_CONTENT_PADDING,
            topPadding = 0,
            bottomPadding = 0
        )
    }

    val equipmentsGridHeight = remember(Equipment.entries.size) {
        calculateGridHeight(
            itemCount = Equipment.entries.size,
            itemHeight = DEFAULT_CHECKBOX_TO_TEXT_HEIGHT,
            gridColumnCount = DEFAULT_GRID_COLUMNS_COUNT,
            verticalSpacing = DEFAULT_GRID_CONTENT_PADDING,
            topPadding = 0,
            bottomPadding = 0
        )
    }

    val musclesGridHeight = remember(Muscle.entries.size) {
        calculateGridHeight(
            itemCount = Muscle.entries.size,
            itemHeight = DEFAULT_CHECKBOX_TO_TEXT_HEIGHT,
            gridColumnCount = DEFAULT_GRID_COLUMNS_COUNT,
            verticalSpacing = DEFAULT_GRID_CONTENT_PADDING,
            topPadding = 0,
            bottomPadding = 0
        )
    }

    val timeFiltersGridHeight = remember(WorkoutTimeFilter.entries.size) {
        calculateGridHeight(
            itemCount = WorkoutTimeFilter.entries.size,
            itemHeight = 40,
            gridColumnCount = 2,
            verticalSpacing = DEFAULT_GRID_CONTENT_PADDING,
            topPadding = 0,
            bottomPadding = 0,
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = WorkoutFlowTheme.padding.default),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.workout_types),
                        style = WorkoutFlowTheme.typography.labelLarge,
                    )
                    Text(
                        text = selectedWorkoutTypes.size.toString(),
                        style = WorkoutFlowTheme.typography.labelSmall.copy(
                            brush = WorkoutFlowTheme.colors.brandColors.primaryGdVert
                        ),
                    )
                }
                Spacer(Modifier.height(WorkoutFlowTheme.padding.medium))
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(workoutTypesGridHeight.dp),
                    columns = GridCells.Fixed(DEFAULT_GRID_COLUMNS_COUNT),
                    horizontalArrangement = Arrangement.spacedBy(DEFAULT_GRID_CONTENT_PADDING.dp),
                    verticalArrangement = Arrangement.spacedBy(DEFAULT_GRID_CONTENT_PADDING.dp),
                    userScrollEnabled = false,
                ) {
                    items(WorkoutType.entries, key = { it.ordinal }) { workoutType ->
                        CheckboxToText(
                            text = workoutType.getName(),
                            checked = workoutType in selectedWorkoutTypes,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    selectedWorkoutTypes.add(workoutType)
                                } else {
                                    selectedWorkoutTypes.remove(workoutType)
                                }
                            },
                            checkboxSize = 20.dp,
                            checkmarkSize = 14.dp,
                        )
                    }
                }
            }
            Spacer(Modifier.height(WorkoutFlowTheme.padding.medium))
            HorizontalDivider()
            Spacer(Modifier.height(WorkoutFlowTheme.padding.medium))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = WorkoutFlowTheme.padding.default),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.equipment),
                        style = WorkoutFlowTheme.typography.labelLarge,
                    )
                    Text(
                        text = selectedEquipments.size.toString(),
                        style = WorkoutFlowTheme.typography.labelSmall.copy(
                            brush = WorkoutFlowTheme.colors.brandColors.primaryGdVert
                        ),
                    )
                }
                Spacer(Modifier.height(WorkoutFlowTheme.padding.medium))
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(equipmentsGridHeight.dp),
                    columns = GridCells.Fixed(DEFAULT_GRID_COLUMNS_COUNT),
                    horizontalArrangement = Arrangement.spacedBy(DEFAULT_GRID_CONTENT_PADDING.dp),
                    verticalArrangement = Arrangement.spacedBy(DEFAULT_GRID_CONTENT_PADDING.dp),
                    userScrollEnabled = false,
                ) {
                    items(Equipment.entries, key = { it.ordinal }) { equipment ->
                        CheckboxToText(
                            text = equipment.getName(),
                            checked = equipment in selectedEquipments,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    selectedEquipments.add(equipment)
                                } else {
                                    selectedEquipments.remove(equipment)
                                }
                            },
                            checkboxSize = 20.dp,
                            checkmarkSize = 14.dp,
                        )
                    }
                }
            }
            Spacer(Modifier.height(WorkoutFlowTheme.padding.medium))
            HorizontalDivider()
            Spacer(Modifier.height(WorkoutFlowTheme.padding.medium))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = WorkoutFlowTheme.padding.default),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.targeted_muscles),
                        style = WorkoutFlowTheme.typography.labelLarge,
                    )
                    Text(
                        text = selectedMuscles.size.toString(),
                        style = WorkoutFlowTheme.typography.labelSmall.copy(
                            brush = WorkoutFlowTheme.colors.brandColors.primaryGdVert
                        ),
                    )
                }
                Spacer(Modifier.height(WorkoutFlowTheme.padding.medium))
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(musclesGridHeight.dp),
                    columns = GridCells.Fixed(DEFAULT_GRID_COLUMNS_COUNT),
                    horizontalArrangement = Arrangement.spacedBy(DEFAULT_GRID_CONTENT_PADDING.dp),
                    verticalArrangement = Arrangement.spacedBy(DEFAULT_GRID_CONTENT_PADDING.dp),
                    userScrollEnabled = false
                ) {
                    items(Muscle.entries, key = { it.ordinal }) { muscle ->
                        CheckboxToText(
                            text = muscle.getName(),
                            checked = muscle in selectedMuscles,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    selectedMuscles.add(muscle)
                                } else {
                                    selectedMuscles.remove(muscle)
                                }
                            },
                            checkboxSize = 20.dp,
                            checkmarkSize = 14.dp,
                        )
                    }
                }
            }
            Spacer(Modifier.height(WorkoutFlowTheme.padding.medium))
            HorizontalDivider()
            Spacer(Modifier.height(WorkoutFlowTheme.padding.medium))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = WorkoutFlowTheme.padding.default),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.duration),
                        style = WorkoutFlowTheme.typography.labelLarge,
                    )
                    Text(
                        text = selectedWorkoutTimeFilters.size.toString(),
                        style = WorkoutFlowTheme.typography.labelSmall.copy(
                            brush = WorkoutFlowTheme.colors.brandColors.primaryGdVert
                        ),
                    )
                }
                Spacer(Modifier.height(WorkoutFlowTheme.padding.default))
                LazyVerticalGrid(
                    modifier = Modifier.height(timeFiltersGridHeight.dp),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(DEFAULT_GRID_CONTENT_PADDING.dp),
                    verticalArrangement = Arrangement.spacedBy(DEFAULT_GRID_CONTENT_PADDING.dp),
                    userScrollEnabled = false
                ) {
                    items(WorkoutTimeFilter.entries, key = { it.ordinal }) { timeFilter ->
                        val selected = timeFilter in selectedWorkoutTimeFilters
                        SelectableGradientTextButton(
                            text = timeFilter.getName(),
                            selected = selected,
                            onClick = {
                                if (selected) {
                                    selectedWorkoutTimeFilters.remove(timeFilter)
                                } else {
                                    selectedWorkoutTimeFilters.add(timeFilter)
                                }
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(100.dp))
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(vertical = WorkoutFlowTheme.padding.medium)
                .background(WorkoutFlowTheme.colors.surfaceColors.surfaceHigh.copy(0.95f))
                .padding(horizontal = WorkoutFlowTheme.padding.default),
            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GradientButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    selectedMuscles.clear()
                    selectedEquipments.clear()
                    selectedWorkoutTypes.clear()
                    selectedWorkoutTimeFilters.clear()
                },
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
                onClick = {
                    onApplyClick(
                        WorkoutsFilter(
                            selectedMuscles = selectedMuscles,
                            selectedEquipments = selectedEquipments,
                            selectedWorkoutTypes = selectedWorkoutTypes,
                            selectedTimeFilters = selectedWorkoutTimeFilters
                        )
                    )
                },
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