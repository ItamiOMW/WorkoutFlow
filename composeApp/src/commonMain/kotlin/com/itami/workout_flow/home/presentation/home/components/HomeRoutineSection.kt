package com.itami.workout_flow.home.presentation.home.components

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.domain.model.workout.ScheduledWorkout
import com.itami.workout_flow.core.presentation.components.GradientSurface
import com.itami.workout_flow.core.presentation.components.WorkoutPreviewItem
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.click_edit_to_schedule_workout
import workoutflow.composeapp.generated.resources.edit
import workoutflow.composeapp.generated.resources.icon_arrow_next
import workoutflow.composeapp.generated.resources.my_routine
import workoutflow.composeapp.generated.resources.no_workout_for_this_day

@Composable
fun HomeRoutineSection(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    selectedRoutineDay: RoutineDayUI,
    routineDays: List<RoutineDayUI>,
    onScheduledWorkoutClick: (ScheduledWorkout) -> Unit,
    onEditRoutineClick: () -> Unit,
    onRoutineDayClick: (RoutineDayUI) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = WorkoutFlowTheme.padding.default)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.my_routine),
                style = WorkoutFlowTheme.typography.labelLarge,
                color = WorkoutFlowTheme.colors.backgroundColors.onBackground,
            )
            Surface(
                onClick = onEditRoutineClick,
                color = Color.Transparent,
                shape = WorkoutFlowTheme.shapes.small
            ) {
                Row(
                    modifier = Modifier.padding(WorkoutFlowTheme.padding.tiny)
                ) {
                    Text(
                        text = stringResource(Res.string.edit),
                        style = WorkoutFlowTheme.typography.labelSmall,
                        color = WorkoutFlowTheme.colors.backgroundColors.onBackground
                    )
                    Spacer(modifier = Modifier.width(WorkoutFlowTheme.padding.tiny))
                    Icon(
                        painter = painterResource(Res.drawable.icon_arrow_next),
                        contentDescription = stringResource(Res.string.edit),
                        tint = WorkoutFlowTheme.colors.backgroundColors.onBackground,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.default))
        Row(
            modifier = Modifier
                .padding(horizontal = WorkoutFlowTheme.padding.default)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.extraSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            routineDays.forEach { routineDay ->
                RoutineDayItem(
                    modifier = Modifier.weight(1f),
                    routineDay = routineDay,
                    selected = routineDay == selectedRoutineDay,
                    isToday = routineDay.date == currentDate,
                    hasScheduledWorkouts = routineDay.scheduledWorkouts.isNotEmpty(),
                    onClick = { onRoutineDayClick(routineDay) },
                )
            }
        }
        Spacer(modifier = Modifier.height(WorkoutFlowTheme.padding.medium))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            horizontalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.default),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(WorkoutFlowTheme.padding.default)
        ) {
            if (selectedRoutineDay.scheduledWorkouts.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(vertical = WorkoutFlowTheme.padding.medium),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(Res.string.no_workout_for_this_day),
                            style = WorkoutFlowTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = buildAnnotatedString {
                                val edit = stringResource(Res.string.edit)
                                val clickEditMessage = stringResource(Res.string.click_edit_to_schedule_workout, edit)
                                append(clickEditMessage)
                                val editStart = clickEditMessage.indexOf(edit)
                                addStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold),
                                    start = editStart,
                                    end = editStart + edit.length
                                )
                            },
                            style = WorkoutFlowTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            items(selectedRoutineDay.scheduledWorkouts, key = { it.id }) { scheduledWorkout ->
                WorkoutPreviewItem(
                    modifier = Modifier
                        .fillParentMaxWidth(
                            if (selectedRoutineDay.scheduledWorkouts.size == 1) {
                                1f
                            } else {
                                0.85f
                            }
                        ),
                    workoutPreview = scheduledWorkout.workoutPreview,
                    onClick = { onScheduledWorkoutClick(scheduledWorkout) }
                )
            }
        }
    }
}

@Composable
private fun RoutineDayItem(
    modifier: Modifier = Modifier,
    routineDay: RoutineDayUI,
    selected: Boolean,
    isToday: Boolean,
    hasScheduledWorkouts: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        GradientSurface(
            modifier = Modifier.fillMaxWidth(),
            selected = selected,
            onClick = onClick,
            brush = if (selected) {
                WorkoutFlowTheme.colors.brandColors.primaryGdVertContainer
            } else {
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Transparent
                    )
                )
            },
        ) {
            Column(
                modifier = Modifier.padding(vertical = WorkoutFlowTheme.padding.tiny),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = routineDay.date.format(
                        LocalDate.Format {
                            dayOfWeek(names = DayOfWeekNames.ENGLISH_ABBREVIATED)
                        }
                    ),
                    style = WorkoutFlowTheme.typography.bodySmall.copy(
                        brush = if (selected or isToday) {
                            WorkoutFlowTheme.colors.brandColors.primaryGdVert
                        } else {
                            Brush.verticalGradient(
                                colors = listOf(
                                    WorkoutFlowTheme.colors.backgroundColors.onBackground,
                                    WorkoutFlowTheme.colors.backgroundColors.onBackground
                                )
                            )
                        }
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = routineDay.date.format(
                        LocalDate.Format {
                            dayOfMonth()
                        }
                    ),
                    style = WorkoutFlowTheme.typography.labelMedium.copy(
                        brush = if (selected or isToday) {
                            WorkoutFlowTheme.colors.brandColors.primaryGdVert
                        } else {
                            Brush.verticalGradient(
                                colors = listOf(
                                    WorkoutFlowTheme.colors.backgroundColors.onBackground,
                                    WorkoutFlowTheme.colors.backgroundColors.onBackground
                                )
                            )
                        }
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.padding(WorkoutFlowTheme.padding.tiny))
        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .then(
                    if (hasScheduledWorkouts) {
                        if (selected or isToday) {
                            Modifier.background(WorkoutFlowTheme.colors.brandColors.primaryGdVert)
                        } else {
                            Modifier.background(WorkoutFlowTheme.colors.backgroundColors.onBackground)
                        }
                    } else {
                        Modifier.background(Color.Transparent)
                    }
                )
        )
    }
}