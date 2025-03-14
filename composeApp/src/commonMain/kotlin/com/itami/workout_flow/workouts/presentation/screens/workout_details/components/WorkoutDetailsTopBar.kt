package com.itami.workout_flow.workouts.presentation.screens.workout_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.itami.workout_flow.core.presentation.components.GradientOutlinedText
import com.itami.workout_flow.core.presentation.components.IconToText
import com.itami.workout_flow.core.presentation.components.collapsing_top_bar.CollapsingTopAppBarScrollBehavior
import com.itami.workout_flow.core.presentation.components.collapsing_top_bar.CollapsingTopBar
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.core.presentation.utils.getName
import com.itami.workout_flow.workouts.presentation.screens.workout_details.WorkoutDetailsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.add_to_favorite
import workoutflow.composeapp.generated.resources.author_profile
import workoutflow.composeapp.generated.resources.author_profile_picture
import workoutflow.composeapp.generated.resources.category_icon
import workoutflow.composeapp.generated.resources.clock_icon
import workoutflow.composeapp.generated.resources.close
import workoutflow.composeapp.generated.resources.duration_min
import workoutflow.composeapp.generated.resources.edit_workout
import workoutflow.composeapp.generated.resources.exercise_icon
import workoutflow.composeapp.generated.resources.favorite
import workoutflow.composeapp.generated.resources.guest
import workoutflow.composeapp.generated.resources.icon_arrow_back
import workoutflow.composeapp.generated.resources.icon_bodybuilder
import workoutflow.composeapp.generated.resources.icon_category
import workoutflow.composeapp.generated.resources.icon_clock
import workoutflow.composeapp.generated.resources.icon_exercise
import workoutflow.composeapp.generated.resources.icon_favorite
import workoutflow.composeapp.generated.resources.icon_more_vert
import workoutflow.composeapp.generated.resources.muscles_icon
import workoutflow.composeapp.generated.resources.open_dropdown
import workoutflow.composeapp.generated.resources.remove_from_favorite
import workoutflow.composeapp.generated.resources.start_workout
import workoutflow.composeapp.generated.resources.unknown_user
import workoutflow.composeapp.generated.resources.you

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkoutDetailsTopBar(
    topBarScrollBehavior: CollapsingTopAppBarScrollBehavior,
    state: WorkoutDetailsState,
    onNavigateBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onMoreOptionsClick: () -> Unit,
    onDismissDropdownRequest: () -> Unit,
    onAuthorProfileImageClick: () -> Unit,
    onStartWorkoutDropdownItemClick: () -> Unit,
    onEditWorkoutDropdownItemClick: () -> Unit,
    onAuthorProfileDropdownItemClick: () -> Unit,
) {
    CollapsingTopBar(
        scrollBehavior = topBarScrollBehavior,
        titleText = if (state is WorkoutDetailsState.Workout) {
            state.workoutUI.name
        } else {
            null
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBackClick
            ) {
                Icon(
                    painter = painterResource(Res.drawable.icon_arrow_back),
                    tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                    contentDescription = stringResource(Res.string.close),
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            if (state is WorkoutDetailsState.Workout) {
                IconButton(
                    onClick = onFavoriteClick
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_favorite),
                        tint = if (state.workoutUI.isFavorite) WorkoutFlowTheme.colors.generalColors.pink
                        else WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        contentDescription = if (state.workoutUI.isFavorite) {
                            stringResource(Res.string.remove_from_favorite)
                        } else {
                            stringResource(Res.string.add_to_favorite)
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(
                    onClick = onMoreOptionsClick
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_more_vert),
                        tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                        contentDescription = stringResource(Res.string.open_dropdown),
                        modifier = Modifier.size(24.dp)
                    )
                    DropdownMenu(
                        expanded = state.dropdownExpanded,
                        onDismissRequest = onDismissDropdownRequest,
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
                                    text = stringResource(Res.string.start_workout),
                                    style = WorkoutFlowTheme.typography.labelMedium,
                                    color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                )
                            },
                            onClick = onStartWorkoutDropdownItemClick,
                        )
                        if (state.workoutUI.isCreatedByCurrentUser) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(Res.string.edit_workout),
                                        style = WorkoutFlowTheme.typography.labelMedium,
                                        color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                    )
                                },
                                onClick = onEditWorkoutDropdownItemClick,
                            )
                        }
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(Res.string.author_profile),
                                    style = WorkoutFlowTheme.typography.labelMedium,
                                    color = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                                )
                            },
                            onClick = onAuthorProfileDropdownItemClick,
                        )
                    }
                }
            }
        },
        additionalContent = {
            if (state is WorkoutDetailsState.Workout) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.small))
                    Text(
                        text = state.workoutUI.description,
                        style = WorkoutFlowTheme.typography.bodyMedium,
                        color = WorkoutFlowTheme.colors.surfaceColors.onSurface
                    )
                    Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.default))
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .clickable { onAuthorProfileImageClick() },
                            model = state.workoutUI.author?.profilePictureUrl,
                            contentDescription = stringResource(Res.string.author_profile_picture),
                            contentScale = ContentScale.FillBounds,
                            placeholder = painterResource(Res.drawable.guest),
                        )
                        Text(
                            text = when {
                                state.workoutUI.author?.name != null -> state.workoutUI.author.name
                                state.workoutUI.isCreatedByCurrentUser -> stringResource(Res.string.you)
                                else -> stringResource(Res.string.unknown_user)
                            },
                            style = WorkoutFlowTheme.typography.labelLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = WorkoutFlowTheme.colors.surfaceColors.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.default))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.default)
                    ) {
                        IconToText(
                            iconPainter = painterResource(Res.drawable.icon_clock),
                            iconDescription = stringResource(Res.string.clock_icon),
                            iconSize = 20.dp,
                            iconColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            text = stringResource(
                                Res.string.duration_min,
                                state.workoutUI.durationMin
                            ),
                            textColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            textStyle = WorkoutFlowTheme.typography.labelMedium,
                        )
                        IconToText(
                            iconPainter = painterResource(Res.drawable.icon_favorite),
                            iconDescription = stringResource(Res.string.favorite),
                            iconSize = 20.dp,
                            iconColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            text = state.workoutUI.favoritesCount.toString(),
                            textColor = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            textStyle = WorkoutFlowTheme.typography.labelMedium,
                        )
                    }
                    Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.default))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                        verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.icon_exercise),
                            contentDescription = stringResource(Res.string.exercise_icon),
                            tint = WorkoutFlowTheme.colors.surfaceColors.onSurface,
                            modifier = Modifier.size(24.dp).align(Alignment.CenterVertically)
                        )
                        state.workoutUI.equipments.forEach { equipment ->
                            GradientOutlinedText(
                                text = equipment.getName(),
                                modifier = Modifier.align(Alignment.CenterVertically)
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
                            modifier = Modifier.size(24.dp).align(Alignment.CenterVertically)
                        )
                        state.workoutUI.workoutTypes.forEach { type ->
                            GradientOutlinedText(
                                text = type.getName(),
                                modifier = Modifier.align(Alignment.CenterVertically)
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
                            modifier = Modifier.size(24.dp).align(Alignment.CenterVertically)
                        )
                        state.workoutUI.muscles.forEach { muscle ->
                            GradientOutlinedText(
                                text = muscle.getName(),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.small))
                }
            }
        },
    )
}