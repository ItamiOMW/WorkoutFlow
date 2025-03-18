package com.itami.workout_flow.workouts.presentation.screens.workout_editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.presentation.components.GradientOutlinedText
import com.itami.workout_flow.core.presentation.components.button.GradientButton
import com.itami.workout_flow.core.presentation.components.button.GradientButtonColors
import com.itami.workout_flow.core.presentation.components.collapsing_top_bar.CollapsingTopAppBarScrollBehavior
import com.itami.workout_flow.core.presentation.components.collapsing_top_bar.CollapsingTopBarWithEditableTitle
import com.itami.workout_flow.core.presentation.components.switch.GradientSwitch
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.core.presentation.utils.getName
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.category_icon
import workoutflow.composeapp.generated.resources.clock_icon
import workoutflow.composeapp.generated.resources.delete_workout
import workoutflow.composeapp.generated.resources.description
import workoutflow.composeapp.generated.resources.duration_min
import workoutflow.composeapp.generated.resources.edit
import workoutflow.composeapp.generated.resources.edit_duration
import workoutflow.composeapp.generated.resources.edit_equipment
import workoutflow.composeapp.generated.resources.edit_muscles
import workoutflow.composeapp.generated.resources.edit_workout_types
import workoutflow.composeapp.generated.resources.exercise_icon
import workoutflow.composeapp.generated.resources.go_back
import workoutflow.composeapp.generated.resources.icon_arrow_back
import workoutflow.composeapp.generated.resources.icon_bodybuilder
import workoutflow.composeapp.generated.resources.icon_category
import workoutflow.composeapp.generated.resources.icon_check
import workoutflow.composeapp.generated.resources.icon_clock
import workoutflow.composeapp.generated.resources.icon_edit
import workoutflow.composeapp.generated.resources.icon_exercise
import workoutflow.composeapp.generated.resources.icon_more_vert
import workoutflow.composeapp.generated.resources.icon_visibility
import workoutflow.composeapp.generated.resources.muscles_icon
import workoutflow.composeapp.generated.resources.save_workout
import workoutflow.composeapp.generated.resources.show_more_options
import workoutflow.composeapp.generated.resources.visibility_icon
import workoutflow.composeapp.generated.resources.visible_to_others
import workoutflow.composeapp.generated.resources.workout_name

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun WorkoutEditorTopBar(
    topBarScrollBehavior: CollapsingTopAppBarScrollBehavior,
    workoutName: String,
    workoutDesc: String,
    equipments: List<Equipment>,
    workoutTypes: List<WorkoutType>,
    muscles: List<Muscle>,
    durationMin: Int?,
    isVisibleToOthers: Boolean,
    isEditMode: Boolean,
    onWorkoutNameChange: (newValue: String) -> Unit,
    onWorkoutDescChange: (newValue: String) -> Unit,
    onEditDurationClick: () -> Unit,
    onEditEquipmentClick: () -> Unit,
    onEditWorkoutTypesClick: () -> Unit,
    onEditMusclesClick: () -> Unit,
    onVisibleToOthersChange: (isVisible: Boolean) -> Unit,
    onSaveWorkoutClick: () -> Unit,
    onDeleteWorkoutClick: () -> Unit,
    onNavigateBackClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    var dropdownMenuExpanded by remember { mutableStateOf(false) }

    CollapsingTopBarWithEditableTitle(
        scrollBehavior = topBarScrollBehavior,
        titleText = workoutName,
        onTitleValueChange = onWorkoutNameChange,
        titleHint = stringResource(Res.string.workout_name),
        expandedTitleStyle = WorkoutFlowTheme.typography.titleMedium.copy(
            color = WorkoutFlowTheme.colors.surfaceColors.onSurface
        ),
        collapsedTitleStyle = WorkoutFlowTheme.typography.titleSmall.copy(
            color = WorkoutFlowTheme.colors.surfaceColors.onSurface
        ),
        navigationIcon = {
            IconButton(
                onClick = onNavigateBackClick
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_arrow_back),
                    tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                    contentDescription = stringResource(Res.string.go_back),
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSaveWorkoutClick
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_check),
                    tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                    contentDescription = stringResource(Res.string.save_workout),
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(
                onClick = {
                    dropdownMenuExpanded = true
                }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_more_vert),
                    tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                    contentDescription = stringResource(Res.string.show_more_options),
                    modifier = Modifier.size(24.dp)
                )
                DropdownMenu(
                    expanded = dropdownMenuExpanded,
                    onDismissRequest = {
                        dropdownMenuExpanded = false
                    },
                    containerColor = if (WorkoutFlowTheme.isDarkTheme) {
                        WorkoutFlowTheme.colors.surfaceColors.surfaceLow
                    } else {
                        WorkoutFlowTheme.colors.surfaceColors.surfaceHigh
                    },
                    shape = WorkoutFlowTheme.shapes.small,
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(Res.string.edit_duration),
                                style = WorkoutFlowTheme.typography.labelMedium,
                                color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            )
                        },
                        onClick = {
                            dropdownMenuExpanded = false
                            onEditDurationClick()
                        },
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(Res.string.edit_equipment),
                                style = WorkoutFlowTheme.typography.labelMedium,
                                color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            )
                        },
                        onClick = {
                            dropdownMenuExpanded = false
                            onEditEquipmentClick()
                        },
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(Res.string.edit_workout_types),
                                style = WorkoutFlowTheme.typography.labelMedium,
                                color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            )
                        },
                        onClick = {
                            dropdownMenuExpanded = false
                            onEditWorkoutTypesClick()
                        },
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(Res.string.edit_muscles),
                                style = WorkoutFlowTheme.typography.labelMedium,
                                color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            )
                        },
                        onClick = {
                            dropdownMenuExpanded = false
                            onEditMusclesClick()
                        },
                    )
                    if (isEditMode) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(Res.string.delete_workout),
                                    style = WorkoutFlowTheme.typography.labelMedium,
                                    color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                )
                            },
                            onClick = {
                                dropdownMenuExpanded = false
                                onDeleteWorkoutClick()
                            },
                        )
                    }

                }
            }
        },
        additionalContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(Modifier.height(WorkoutFlowTheme.padding.default))
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = workoutDesc,
                    onValueChange = onWorkoutDescChange,
                    textStyle = WorkoutFlowTheme.typography.bodyLarge.copy(
                        color = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant
                    ),
                    cursorBrush = Brush.linearGradient(
                        colors = listOf(
                            WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            WorkoutFlowTheme.colors.surfaceColors.onSurface
                        )
                    ),
                    maxLines = 7,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    decorationBox = { innerTextField ->
                        TextFieldDefaults.DecorationBox(
                            value = workoutDesc,
                            singleLine = false,
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                                unfocusedTextColor = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            ),
                            placeholder = {
                                Text(
                                    text = stringResource(Res.string.description),
                                    style = WorkoutFlowTheme.typography.bodyLarge,
                                    color = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                                )
                            },
                            innerTextField = innerTextField,
                            enabled = true,
                            visualTransformation = VisualTransformation.None,
                            interactionSource = remember { MutableInteractionSource() },
                            contentPadding = PaddingValues(0.dp),
                        )
                    }
                )
                Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.extraSmall))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_clock),
                        contentDescription = stringResource(Res.string.clock_icon),
                        tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        modifier = Modifier.size(24.dp)
                    )
                    Surface(
                        onClick = onEditDurationClick,
                        color = Color.Transparent,
                        shape = WorkoutFlowTheme.shapes.small,
                    ) {
                        Text(
                            text = if (durationMin == null) {
                                stringResource(Res.string.edit_duration)
                            } else {
                                stringResource(Res.string.duration_min, durationMin)
                            },
                            color = if (durationMin == null) {
                                WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant
                            } else {
                                WorkoutFlowTheme.colors.surfaceColors.onSurface
                            },
                            style = WorkoutFlowTheme.typography.labelMedium,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.extraSmall))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_visibility),
                        contentDescription = stringResource(Res.string.visibility_icon),
                        tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = stringResource(Res.string.visible_to_others),
                        color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        style = WorkoutFlowTheme.typography.labelMedium,
                    )
                    GradientSwitch(
                        checked = isVisibleToOthers,
                        onCheckedChange = { checked ->
                            onVisibleToOthersChange(checked)
                        },
                        thumbShape = CircleShape,
                        trackShape = WorkoutFlowTheme.shapes.extraLarge,
                        modifier = Modifier.scale(0.7f),
                    )
                }
                Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.extraSmall))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                    verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_exercise),
                        contentDescription = stringResource(Res.string.exercise_icon),
                        tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                    )
                    equipments.forEach { equipment ->
                        GradientOutlinedText(
                            text = equipment.getName(),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    GradientButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = onEditEquipmentClick,
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
                            painter = painterResource(Res.drawable.icon_edit),
                            contentDescription = stringResource(Res.string.edit),
                            modifier = Modifier.size(16.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.default))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                    verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_category),
                        contentDescription = stringResource(Res.string.category_icon),
                        tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                    )
                    workoutTypes.forEach { type ->
                        GradientOutlinedText(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = type.getName()
                        )
                    }
                    GradientButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = onEditWorkoutTypesClick,
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
                            painter = painterResource(Res.drawable.icon_edit),
                            contentDescription = stringResource(Res.string.edit),
                            modifier = Modifier.size(16.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.default))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                    verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_bodybuilder),
                        contentDescription = stringResource(Res.string.muscles_icon),
                        tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                    )
                    muscles.forEach { muscle ->
                        GradientOutlinedText(
                            text = muscle.getName(),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    GradientButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = onEditMusclesClick,
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
                            painter = painterResource(Res.drawable.icon_edit),
                            contentDescription = stringResource(Res.string.edit),
                            modifier = Modifier.size(16.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.small))
            }
        },
    )
}