package com.itami.workout_flow.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.itami.workout_flow.core.domain.model.workout.WorkoutPreview
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.core.presentation.utils.joinMuscleNames
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.author_profile_picture
import workoutflow.composeapp.generated.resources.duration
import workoutflow.composeapp.generated.resources.duration_min
import workoutflow.composeapp.generated.resources.favorite
import workoutflow.composeapp.generated.resources.favorites_count
import workoutflow.composeapp.generated.resources.guest
import workoutflow.composeapp.generated.resources.icon_bodybuilder
import workoutflow.composeapp.generated.resources.icon_clock
import workoutflow.composeapp.generated.resources.icon_favorite
import workoutflow.composeapp.generated.resources.not_favorite
import workoutflow.composeapp.generated.resources.unknown_user
import workoutflow.composeapp.generated.resources.you

@Composable
fun WorkoutPreviewItem(
    modifier: Modifier = Modifier,
    workoutPreview: WorkoutPreview,
    onClick: () -> Unit,
    shape: Shape = WorkoutFlowTheme.shapes.small,
    shadowElevation: Dp = 3.dp
) {
    Surface(
        modifier = modifier,
        color = WorkoutFlowTheme.colors.surfaceColors.surfaceHigh,
        contentColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
        shadowElevation = shadowElevation,
        shape = shape,
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WorkoutFlowTheme.padding.default),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape),
                        model = workoutPreview.author?.profilePictureUrl,
                        contentDescription = stringResource(Res.string.author_profile_picture),
                        contentScale = ContentScale.FillBounds,
                        placeholder = painterResource(Res.drawable.guest),
                        fallback = painterResource(Res.drawable.guest),
                    )
                    Spacer(modifier = Modifier.width(WorkoutFlowTheme.padding.extraSmall))
                    Text(
                        text = when {
                            workoutPreview.author?.name != null -> workoutPreview.author.name
                            workoutPreview.isCreatedByCurrentUser -> stringResource(Res.string.you)
                            else -> stringResource(Res.string.unknown_user)
                        },
                        style = WorkoutFlowTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Icon(
                    painter = painterResource(Res.drawable.icon_favorite),
                    contentDescription = if (workoutPreview.isFavorite) {
                        stringResource(Res.string.favorite)
                    } else {
                        stringResource(Res.string.not_favorite)
                    },
                    tint = if (workoutPreview.isFavorite) {
                        WorkoutFlowTheme.colors.generalColors.pink
                    } else {
                        WorkoutFlowTheme.colors.surfaceColors.onSurface
                    },
                    modifier = Modifier.size(16.dp),
                )
            }
            Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.small))
            Text(
                text = workoutPreview.name,
                style = WorkoutFlowTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.small))
            IconToText(
                iconPainter = painterResource(Res.drawable.icon_bodybuilder),
                iconColor = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                iconSize = 16.dp,
                text = workoutPreview.muscles.joinMuscleNames(),
                textStyle = WorkoutFlowTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.small))
            Row(
                horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconToText(
                    iconPainter = painterResource(Res.drawable.icon_clock),
                    iconColor = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                    iconSize = 16.dp,
                    iconDescription = stringResource(Res.string.duration),
                    text = stringResource(Res.string.duration_min, workoutPreview.durationMin),
                    textStyle = WorkoutFlowTheme.typography.bodySmall,
                    verticalAlignment = Alignment.Top
                )
                IconToText(
                    iconPainter = painterResource(Res.drawable.icon_favorite),
                    iconColor = WorkoutFlowTheme.colors.surfaceColors.onSurfaceVariant,
                    iconSize = 16.dp,
                    iconDescription = stringResource(Res.string.favorites_count),
                    text = workoutPreview.favoritesCount.toString(),
                    textStyle = WorkoutFlowTheme.typography.bodySmall
                )
            }
        }
    }
}