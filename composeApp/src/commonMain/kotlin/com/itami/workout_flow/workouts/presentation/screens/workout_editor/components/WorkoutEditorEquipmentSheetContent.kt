package com.itami.workout_flow.workouts.presentation.screens.workout_editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.components.CheckboxToText
import com.itami.workout_flow.core.presentation.components.button.GradientButton
import com.itami.workout_flow.core.presentation.components.button.GradientButtonColors
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.core.presentation.utils.getName
import com.itami.workout_flow.model.Equipment
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.apply
import workoutflow.composeapp.generated.resources.equipment
import workoutflow.composeapp.generated.resources.reset

@Composable
internal fun WorkoutEditorEquipmentSheetContent(
    modifier: Modifier = Modifier,
    initialEquipment: List<Equipment> = emptyList(),
    onApply: (List<Equipment>) -> Unit,
) {
    val selectedEquipmentState = remember { mutableStateListOf(*initialEquipment.toTypedArray()) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.equipment),
            style = WorkoutFlowTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(WorkoutFlowTheme.padding.large))
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.default),
            verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.default),
            userScrollEnabled = false
        ) {
            items(Equipment.entries, key = { it.ordinal }) { equipment ->
                CheckboxToText(
                    text = equipment.getName(),
                    checked = equipment in selectedEquipmentState,
                    onCheckedChange = { checked ->
                        if (checked) {
                            selectedEquipmentState.add(equipment)
                        } else {
                            selectedEquipmentState.remove(equipment)
                        }
                    },
                    checkboxSize = 20.dp,
                    checkmarkSize = 14.dp,
                )
            }
        }
        Spacer(Modifier.height(WorkoutFlowTheme.padding.extraLarge))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GradientButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    selectedEquipmentState.clear()
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
                    onApply(selectedEquipmentState)
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