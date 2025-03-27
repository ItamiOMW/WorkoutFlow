package com.itami.workout_flow.workouts.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.itami.workout_flow.core.domain.model.workout.DistanceUnit
import com.itami.workout_flow.core.domain.model.workout.WeightUnit
import com.itami.workout_flow.core.presentation.components.ShimmerBox
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
import workoutflow.composeapp.generated.resources.check_icon
import workoutflow.composeapp.generated.resources.collapse
import workoutflow.composeapp.generated.resources.error_image
import workoutflow.composeapp.generated.resources.exercise_execution_gif
import workoutflow.composeapp.generated.resources.expand
import workoutflow.composeapp.generated.resources.hours_short
import workoutflow.composeapp.generated.resources.icon_arrow_down
import workoutflow.composeapp.generated.resources.icon_check
import workoutflow.composeapp.generated.resources.icon_link
import workoutflow.composeapp.generated.resources.link_icon
import workoutflow.composeapp.generated.resources.minutes_short
import workoutflow.composeapp.generated.resources.reps
import workoutflow.composeapp.generated.resources.seconds_short
import workoutflow.composeapp.generated.resources.sets_count
import workoutflow.composeapp.generated.resources.superset_uppercase

@Composable
fun WorkoutExerciseItem(
    modifier: Modifier = Modifier,
    workoutExerciseComponent: WorkoutExerciseComponentUI,
    weightUnit: WeightUnit,
    distanceUnit: DistanceUnit,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    elevation: Dp = 3.dp,
    onClick: () -> Unit = { },
    onExpandedStateChange: (expanded: Boolean) -> Unit,
    onExerciseGifClick: (exerciseId: Long) -> Unit,
) {
    val expandIconRotationAngle by animateFloatAsState(
        targetValue = if (workoutExerciseComponent.expanded) 180f else 0f,
        label = "expandIconRotationAngle",
    )

    val expanded = workoutExerciseComponent.expanded

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
                    WorkoutExerciseWithSets(
                        workoutExercise = workoutExerciseComponent.workoutExercise,
                        distanceUnit = distanceUnit,
                        weightUnit = weightUnit,
                        expanded = expanded,
                        onExerciseGifClick = onExerciseGifClick,
                    )
                }

                is WorkoutExerciseComponentUI.Superset -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(WorkoutFlowTheme.padding.default),
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
                    HorizontalDivider(color = WorkoutFlowTheme.colors.surfaceColors.surfaceLow)
                    workoutExerciseComponent.workoutExercises.forEach { supersetWorkoutExercise ->
                        WorkoutExerciseWithSets(
                            workoutExercise = supersetWorkoutExercise,
                            distanceUnit = distanceUnit,
                            weightUnit = weightUnit,
                            expanded = expanded,
                            onExerciseGifClick = onExerciseGifClick,
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
                            resource = Res.plurals.sets_count,
                            quantity = workoutExerciseComponent.totalSetsCount,
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
private fun WorkoutExerciseWithSets(
    workoutExercise: WorkoutExerciseUI,
    weightUnit: WeightUnit,
    distanceUnit: DistanceUnit,
    expanded: Boolean,
    onExerciseGifClick: (exerciseId: Long) -> Unit
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
            onExerciseGifClick = onExerciseGifClick
        )
        if (expanded) {
            Sets(
                modifier = Modifier.fillMaxWidth(),
                sets = workoutExercise.sets,
                exerciseType = workoutExercise.exercise.exerciseType,
                weightUnit = weightUnit,
                distanceUnit = distanceUnit,
            )
        }
    }
}

@Composable
private fun ExerciseItem(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    onExerciseGifClick: (exerciseId: Long) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterEnd,
        ) {
            val painter = rememberAsyncImagePainter(
                model = exercise.exerciseGifUrl,
                error = painterResource(Res.drawable.error_image)
            )

            val painterState by painter.state.collectAsStateWithLifecycle()

            when (painterState) {
                is AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Loading -> {
                    ShimmerBox(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(shape = WorkoutFlowTheme.shapes.small),
                        shimmerColors = listOf(
                            WorkoutFlowTheme.colors.surfaceColors.surfaceLow.copy(alpha = 1.0f),
                            WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant.copy(alpha = 0.1f),
                            WorkoutFlowTheme.colors.surfaceColors.surfaceLow.copy(alpha = 1.0f),
                        )
                    )
                }

                is AsyncImagePainter.State.Error,
                is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = painter,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(WorkoutFlowTheme.shapes.small)
                            .clickable { onExerciseGifClick(exercise.id) },
                        contentDescription = stringResource(Res.string.exercise_execution_gif),
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }
        }
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
}

@Composable
private fun Sets(
    modifier: Modifier,
    sets: List<SetUI>,
    exerciseType: ExerciseType,
    weightUnit: WeightUnit,
    distanceUnit: DistanceUnit,
) {
    Column(modifier = modifier) {
        sets.forEachIndexed { index, set ->
            SetItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WorkoutFlowTheme.padding.default),
                set = set,
                exerciseType = exerciseType,
                weightUnit = weightUnit,
                distanceUnit = distanceUnit
            )
            if (index - 1 < sets.size) {
                HorizontalDivider(color = WorkoutFlowTheme.colors.surfaceColors.surfaceLow)
            }
        }
    }
}

@Composable
private fun SetItem(
    modifier: Modifier = Modifier,
    set: SetUI,
    exerciseType: ExerciseType,
    weightUnit: WeightUnit,
    distanceUnit: DistanceUnit,
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

        if (exerciseType == ExerciseType.WEIGHT_AND_REPS && set.weightGrams != null) {
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
                    Text(
                        text = weightUnit.fromGrams(set.weightGrams).toString(),
                        style = WorkoutFlowTheme.typography.bodySmall,
                        modifier = Modifier.padding(WorkoutFlowTheme.padding.extraSmall),
                    )
                }
                Text(
                    text = weightUnit.getShortName(),
                    style = WorkoutFlowTheme.typography.bodyMedium,
                )
            }

        }

        if (
            (exerciseType == ExerciseType.WEIGHT_AND_REPS || exerciseType == ExerciseType.REPS) &&
            set.reps != null
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
                    Text(
                        text = set.reps.toString(),
                        style = WorkoutFlowTheme.typography.bodySmall,
                        modifier = Modifier.padding(WorkoutFlowTheme.padding.extraSmall),
                    )
                }
                Text(
                    text = stringResource(Res.string.reps),
                    style = WorkoutFlowTheme.typography.bodyMedium,
                )
            }

        }

        if (exerciseType == ExerciseType.DISTANCE_AND_TIME && set.distanceMeters != null) {
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
                    Text(
                        text = distanceUnit.fromMeters(set.distanceMeters).toString(),
                        style = WorkoutFlowTheme.typography.bodySmall,
                        modifier = Modifier.padding(WorkoutFlowTheme.padding.extraSmall),
                    )
                }
                Text(
                    text = distanceUnit.getShortName(),
                    style = WorkoutFlowTheme.typography.bodyMedium,
                )
            }
        }

        if (
            (exerciseType == ExerciseType.DISTANCE_AND_TIME || exerciseType == ExerciseType.TIME) &&
            set.hours != null &&
            set.minutes != null &&
            set.seconds != null
        ) {
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
                        Text(
                            text = set.hours.toString(),
                            style = WorkoutFlowTheme.typography.bodySmall,
                            modifier = Modifier.padding(WorkoutFlowTheme.padding.extraSmall),
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
                        Text(
                            text = set.minutes.toString(),
                            style = WorkoutFlowTheme.typography.bodySmall,
                            modifier = Modifier.padding(WorkoutFlowTheme.padding.extraSmall),
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
                        Text(
                            text = set.seconds.toString(),
                            style = WorkoutFlowTheme.typography.bodySmall,
                            modifier = Modifier.padding(WorkoutFlowTheme.padding.extraSmall),
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