package com.itami.workout_flow.workouts.presentation.screens.workout_editor.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil3.compose.AsyncImage
import com.itami.workout_flow.core.domain.model.workout.DistanceUnit
import com.itami.workout_flow.core.domain.model.workout.WeightUnit
import com.itami.workout_flow.core.presentation.components.SwipeToDeleteContainer
import com.itami.workout_flow.core.presentation.components.text_field.FloatTextField
import com.itami.workout_flow.core.presentation.components.text_field.IntTextField
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.core.presentation.utils.getName
import com.itami.workout_flow.core.presentation.utils.getShortName
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.workouts.presentation.model.SetUI
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseComponentUI
import com.itami.workout_flow.workouts.presentation.model.WorkoutExerciseUI
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.add_set
import workoutflow.composeapp.generated.resources.check_icon
import workoutflow.composeapp.generated.resources.collapse
import workoutflow.composeapp.generated.resources.delete
import workoutflow.composeapp.generated.resources.detach_from_superset
import workoutflow.composeapp.generated.resources.error_image
import workoutflow.composeapp.generated.resources.exercise_execution_gif
import workoutflow.composeapp.generated.resources.expand
import workoutflow.composeapp.generated.resources.hours_short
import workoutflow.composeapp.generated.resources.icon_add
import workoutflow.composeapp.generated.resources.icon_arrow_down
import workoutflow.composeapp.generated.resources.icon_check
import workoutflow.composeapp.generated.resources.icon_link
import workoutflow.composeapp.generated.resources.icon_more_vert
import workoutflow.composeapp.generated.resources.link_icon
import workoutflow.composeapp.generated.resources.minutes_short
import workoutflow.composeapp.generated.resources.reps
import workoutflow.composeapp.generated.resources.seconds_short
import workoutflow.composeapp.generated.resources.sets_count
import workoutflow.composeapp.generated.resources.show_more_options
import workoutflow.composeapp.generated.resources.superset_uppercase
import workoutflow.composeapp.generated.resources.turn_into_superset

@Composable
fun EditorWorkoutExerciseItem(
    modifier: Modifier = Modifier,
    workoutExerciseComponent: WorkoutExerciseComponentUI,
    weightUnit: WeightUnit,
    distanceUnit: DistanceUnit,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    elevation: Dp = 3.dp,
    onClick: () -> Unit = { },
    onExpandedStateChange: (expanded: Boolean) -> Unit,
    onSetValuesChange: (workoutExerciseId: String, set: SetUI) -> Unit,
    onAddSet: (workoutExerciseId: String) -> Unit,
    onAddSupersetExercise: (supersetId: String) -> Unit,
    onTurnIntoSuperset: (workoutExerciseId: String) -> Unit,
    onDetachFromSuperset: (workoutExerciseId: String) -> Unit,
    onRemoveSet: (workoutExerciseId: String, setId: String) -> Unit,
    onRemoveWorkoutExercise: (id: String) -> Unit,
) {
    val expanded = workoutExerciseComponent.expanded

    val expandIconRotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "expandIconRotationAngle",
    )

    Surface(
        modifier = modifier,
        shape = shape,
        shadowElevation = elevation,
        onClick = onClick,
        color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
        contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
        ) {
            when (workoutExerciseComponent) {
                is WorkoutExerciseComponentUI.Single -> {
                    EditorWorkoutExerciseWithSets(
                        workoutExercise = workoutExerciseComponent.workoutExercise,
                        distanceUnit = distanceUnit,
                        weightUnit = weightUnit,
                        expanded = expanded,
                        onSetChange = { setUI ->
                            onSetValuesChange(workoutExerciseComponent.workoutExerciseId, setUI)
                        },
                        onAddSet = {
                            onAddSet(workoutExerciseComponent.workoutExerciseId)
                        },
                        onRemoveSet = { setId ->
                            onRemoveSet(workoutExerciseComponent.workoutExerciseId, setId)
                        },
                        onTurnIntoSuperset = {
                            onTurnIntoSuperset(workoutExerciseComponent.workoutExerciseId)
                        },
                        onRemoveFromSuperset = {
                            onDetachFromSuperset(workoutExerciseComponent.workoutExerciseId)
                        },
                        onRemoveWorkoutExercise = {
                            onRemoveWorkoutExercise(workoutExerciseComponent.workoutExerciseId)
                        }
                    )
                }
                is WorkoutExerciseComponentUI.Superset -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(WorkoutFlowTheme.padding.default),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_link),
                                contentDescription = stringResource(Res.string.link_icon),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = stringResource(Res.string.superset_uppercase),
                                style = WorkoutFlowTheme.typography.labelLarge,
                            )
                        }
                        IconButton(
                            onClick = {
                                onAddSupersetExercise(workoutExerciseComponent.supersetId)
                            }
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.icon_add),
                                contentDescription = stringResource(Res.string.add_set),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    HorizontalDivider(color = WorkoutFlowTheme.colors.surfaceColors.surfaceLow)
                    workoutExerciseComponent.workoutExercises.forEach { supersetWorkoutExercise ->
                        EditorWorkoutExerciseWithSets(
                            workoutExercise = supersetWorkoutExercise,
                            distanceUnit = distanceUnit,
                            weightUnit = weightUnit,
                            expanded = expanded,
                            onSetChange = { setUI ->
                                onSetValuesChange(supersetWorkoutExercise.id, setUI)
                            },
                            onAddSet = {
                                onAddSet(supersetWorkoutExercise.id)
                            },
                            onRemoveSet = { setId ->
                                onRemoveSet(supersetWorkoutExercise.id, setId)
                            },
                            onTurnIntoSuperset = {},
                            onRemoveFromSuperset = {
                                onDetachFromSuperset(supersetWorkoutExercise.id)
                            },
                            onRemoveWorkoutExercise = {
                                onRemoveWorkoutExercise(supersetWorkoutExercise.id)
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WorkoutFlowTheme.colors.surfaceColors.surfaceLow)
                    .padding(start = WorkoutFlowTheme.padding.default),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_check),
                        contentDescription = stringResource(Res.string.check_icon),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = pluralStringResource(
                            Res.plurals.sets_count,
                            workoutExerciseComponent.totalSetsCount,
                            workoutExerciseComponent.totalSetsCount
                        ),
                        style = WorkoutFlowTheme.typography.labelSmall,
                    )
                }
                IconButton(
                    modifier = Modifier.rotate(expandIconRotationAngle),
                    onClick = {
                        onExpandedStateChange(!expanded)
                    },
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_arrow_down),
                        contentDescription = if (expanded) {
                            stringResource(Res.string.collapse)
                        } else {
                            stringResource(Res.string.expand)
                        },
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EditorWorkoutExerciseWithSets(
    workoutExercise: WorkoutExerciseUI,
    weightUnit: WeightUnit,
    distanceUnit: DistanceUnit,
    expanded: Boolean,
    onAddSet: () -> Unit,
    onSetChange: (set: SetUI) -> Unit,
    onRemoveSet: (setId: String) -> Unit,
    onTurnIntoSuperset: () -> Unit,
    onRemoveFromSuperset: () -> Unit,
    onRemoveWorkoutExercise: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        ExerciseItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WorkoutFlowTheme.padding.default),
            exercise = workoutExercise.exercise,
            isSuperset = workoutExercise.supersetId != null,
            onAddSet = onAddSet,
            onTurnIntoSuperset = onTurnIntoSuperset,
            onRemoveFromSuperset = onRemoveFromSuperset,
            onRemoveExercise = onRemoveWorkoutExercise,
        )
        if (expanded) {
            Sets(
                modifier = Modifier.fillMaxWidth(),
                sets = workoutExercise.sets,
                exerciseType = workoutExercise.exercise.exerciseType,
                weightUnit = weightUnit,
                distanceUnit = distanceUnit,
                onSetChange = onSetChange,
                onRemoveSet = { setId ->
                    onRemoveSet(setId)
                }
            )
        }
    }
}

@Composable
private fun ExerciseItem(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    isSuperset: Boolean,
    onAddSet: () -> Unit,
    onTurnIntoSuperset: () -> Unit,
    onRemoveFromSuperset: () -> Unit,
    onRemoveExercise: () -> Unit,
) {
    var dropdownMenuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
        ) {
            AsyncImage(
                model = exercise.exerciseGifUrl,
                contentDescription = stringResource(Res.string.exercise_execution_gif),
                fallback = painterResource(Res.drawable.error_image),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(64.dp)
                    .clip(WorkoutFlowTheme.shapes.small),
            )
            Spacer(Modifier.width(WorkoutFlowTheme.padding.small))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.tiny)
            ) {
                Text(
                    text = exercise.name,
                    style = WorkoutFlowTheme.typography.labelLarge,
                    color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = exercise.muscleInvolvements
                        .map { it.muscle.getName() }
                        .joinToString(),
                    style = WorkoutFlowTheme.typography.bodyMedium,
                    color = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
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
                            text = stringResource(Res.string.add_set),
                            style = WorkoutFlowTheme.typography.labelMedium,
                            color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        )
                    },
                    onClick = {
                        dropdownMenuExpanded = false
                        onAddSet()
                    },
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(
                                if (isSuperset) {
                                    Res.string.detach_from_superset
                                } else {
                                    Res.string.turn_into_superset
                                }
                            ),
                            style = WorkoutFlowTheme.typography.labelMedium,
                            color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        )
                    },
                    onClick = {
                        dropdownMenuExpanded = false
                        if (isSuperset) {
                            onRemoveFromSuperset()
                        } else {
                            onTurnIntoSuperset()
                        }
                    },
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(Res.string.delete),
                            style = WorkoutFlowTheme.typography.labelMedium,
                            color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        )
                    },
                    onClick = {
                        dropdownMenuExpanded = false
                        onRemoveExercise()
                    },
                )
            }
        }
    }
}

@Composable
private fun Sets(
    modifier: Modifier,
    sets: List<SetUI>,
    exerciseType: ExerciseType,
    weightUnit: WeightUnit,
    distanceUnit: DistanceUnit,
    onSetChange: (set: SetUI) -> Unit,
    onRemoveSet: (setId: String) -> Unit,
) {
    val setHeight = 64.dp

    val height = remember(sets) {
        sets.size * setHeight
    }

    LazyColumn(
        modifier = modifier.height(height),
        userScrollEnabled = false,
    ) {
        itemsIndexed(items = sets, key = { _, set -> set.id }) { index, set ->
            SetItem(
                modifier = Modifier
                    .height(setHeight)
                    .fillMaxWidth()
                    .padding(horizontal = WorkoutFlowTheme.padding.default),
                set = set,
                exerciseType = exerciseType,
                weightUnit = weightUnit,
                distanceUnit = distanceUnit,
                onSetChange = onSetChange,
                onRemoveSet = {
                    onRemoveSet(set.id)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetItem(
    modifier: Modifier = Modifier,
    set: SetUI,
    exerciseType: ExerciseType,
    weightUnit: WeightUnit,
    distanceUnit: DistanceUnit,
    onSetChange: (set: SetUI) -> Unit,
    onRemoveSet: (setId: String) -> Unit,
) {
    SwipeToDeleteContainer(
        item = set,
        onDelete = { setToDelete ->
            onRemoveSet(setToDelete.id)
        },
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(WorkoutFlowTheme.colors.surfaceColors.surfaceLow),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = set.order.toString(),
                    style = WorkoutFlowTheme.typography.bodySmall,
                    modifier = Modifier
                        .sizeIn(minWidth = 30.dp)
                        .padding(WorkoutFlowTheme.padding.extraSmall),
                    textAlign = TextAlign.Center
                )
            }

            if (exerciseType == ExerciseType.WEIGHT_AND_REPS) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.tiny)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(WorkoutFlowTheme.shapes.small)
                            .background(WorkoutFlowTheme.colors.surfaceColors.surfaceLow),
                        contentAlignment = Alignment.Center,
                    ) {
                        FloatTextField(
                            value = set.weightGrams?.let { weightUnit.fromGrams(it) },
                            onValueChange = { weight ->
                                val weightGrams = weight?.let { weightUnit.toGrams(it) }
                                onSetChange(set.copy(weightGrams = weightGrams))
                            },
                            modifier = Modifier
                        )
                    }
                    Text(
                        text = weightUnit.getShortName(),
                        style = WorkoutFlowTheme.typography.bodyMedium,
                    )
                }
            }

            if ((exerciseType == ExerciseType.WEIGHT_AND_REPS || exerciseType == ExerciseType.REPS)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.tiny)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(WorkoutFlowTheme.shapes.small)
                            .background(WorkoutFlowTheme.colors.surfaceColors.surfaceLow),
                        contentAlignment = Alignment.Center,
                    ) {
                        IntTextField(
                            value = set.reps,
                            onValueChange = { reps ->
                                onSetChange(set.copy(reps = reps))
                            },
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                    Text(
                        text = stringResource(Res.string.reps),
                        style = WorkoutFlowTheme.typography.bodyMedium,
                    )
                }

            }

            if (exerciseType == ExerciseType.DISTANCE_AND_TIME) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.tiny)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(WorkoutFlowTheme.shapes.small)
                            .background(WorkoutFlowTheme.colors.surfaceColors.surfaceLow),
                        contentAlignment = Alignment.Center,
                    ) {
                        FloatTextField(
                            value = set.distanceMeters?.let { distanceUnit.fromMeters(it) },
                            onValueChange = { distance ->
                                val distanceMeters = distance?.let { distanceUnit.toMeters(it) }
                                onSetChange(set.copy(distanceMeters = distanceMeters))
                            },
                            modifier = Modifier
                        )
                    }
                    Text(
                        text = distanceUnit.getShortName(),
                        style = WorkoutFlowTheme.typography.bodyMedium,
                    )
                }
            }

            if ((exerciseType == ExerciseType.DISTANCE_AND_TIME || exerciseType == ExerciseType.TIME)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.tiny)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(WorkoutFlowTheme.shapes.small)
                                .background(WorkoutFlowTheme.colors.surfaceColors.surfaceLow),
                            contentAlignment = Alignment.Center,
                        ) {
                            IntTextField(
                                value = set.hours,
                                onValueChange = { hours ->
                                    onSetChange(set.copy(hours = hours))
                                },
                                modifier = Modifier
                            )
                        }
                        Text(
                            text = stringResource(Res.string.hours_short),
                            style = WorkoutFlowTheme.typography.bodyMedium,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.tiny)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(WorkoutFlowTheme.shapes.small)
                                .background(WorkoutFlowTheme.colors.surfaceColors.surfaceLow),
                            contentAlignment = Alignment.Center,
                        ) {
                            IntTextField(
                                value = set.minutes,
                                onValueChange = { minutes ->
                                    onSetChange(set.copy(minutes = minutes))
                                },
                                modifier = Modifier
                            )
                        }
                        Text(
                            text = stringResource(Res.string.minutes_short),
                            style = WorkoutFlowTheme.typography.bodyMedium,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.tiny)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(WorkoutFlowTheme.shapes.small)
                                .background(WorkoutFlowTheme.colors.surfaceColors.surfaceLow),
                            contentAlignment = Alignment.Center,
                        ) {
                            IntTextField(
                                value = set.seconds,
                                onValueChange = { seconds ->
                                    onSetChange(set.copy(seconds = seconds))
                                },
                                modifier = Modifier
                            )
                        }
                        Text(
                            text = stringResource(Res.string.seconds_short),
                            style = WorkoutFlowTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }

}