package com.itami.workout_flow.workouts.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.itami.workout_flow.core.presentation.components.ShimmerBox
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.core.presentation.utils.getName
import com.itami.workout_flow.model.Exercise
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.error_image
import workoutflow.composeapp.generated.resources.exercise_execution_gif

@Composable
fun ExerciseItem(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    containerColor: Color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
    contentColor: Color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    shadowElevation: Dp = 3.dp,
    onExerciseClick: () -> Unit,
    onExerciseGifClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        shape = shape,
        shadowElevation = shadowElevation,
        onClick = onExerciseClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WorkoutFlowTheme.padding.small),
            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ExerciseGif(
                modifier = Modifier,
                exerciseGifUrl = exercise.exerciseGifUrl,
                onClick = onExerciseGifClick
            )
            ExerciseDetails(
                modifier = Modifier.fillMaxWidth(),
                exercise = exercise
            )
        }
    }
}

@Composable
private fun ExerciseDetails(
    modifier: Modifier,
    exercise: Exercise,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall)
    ) {
        Text(
            text = exercise.name,
            style = WorkoutFlowTheme.typography.labelMedium,
            color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = exercise.muscleInvolvements
                .map { it.muscle.getName() }
                .joinToString(),
            style = WorkoutFlowTheme.typography.bodySmall,
            color = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = exercise.exerciseType.getName(),
            style = WorkoutFlowTheme.typography.bodySmall,
            color = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
private fun ExerciseGif(
    modifier: Modifier,
    exerciseGifUrl: String?,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd,
    ) {
        val painter = rememberAsyncImagePainter(
            model = exerciseGifUrl,
            error = painterResource(Res.drawable.error_image)
        )

        val painterState by painter.state.collectAsStateWithLifecycle()

        when (painterState) {
            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Loading -> {
                ShimmerBox(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(shape = WorkoutFlowTheme.shapes.small)
                        .clickable { onClick() },
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
                        .clickable { onClick() },
                    contentDescription = stringResource(Res.string.exercise_execution_gif),
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }
}