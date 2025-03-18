package com.itami.workout_flow.workouts.presentation.screens.workout_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itami.workout_flow.core.domain.model.workout.DistanceUnit
import com.itami.workout_flow.core.domain.model.workout.WeightUnit
import com.itami.workout_flow.core.presentation.theme.WorkoutFlowTheme
import com.itami.workout_flow.workouts.presentation.components.WorkoutExerciseItem
import com.itami.workout_flow.workouts.presentation.model.WorkoutUI

@Composable
fun WorkoutDetailsExerciseList(
    modifier: Modifier = Modifier,
    workout: WorkoutUI,
    weightUnit: WeightUnit,
    distanceUnit: DistanceUnit,
    lazyListState: LazyListState,
    onExpandedStateChange: (workoutExerciseId: String, expanded: Boolean) -> Unit,
    onExerciseGifClick: (exerciseId: Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = WorkoutFlowTheme.padding.medium),
        verticalArrangement = Arrangement.spacedBy(WorkoutFlowTheme.padding.medium),
        state = lazyListState
    ) {
        items(workout.workoutExercises, key = { it.workoutExerciseId }) { workoutExerciseComponent ->
            WorkoutExerciseItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = WorkoutFlowTheme.padding.default),
                workoutExerciseComponent = workoutExerciseComponent,
                weightUnit = weightUnit,
                distanceUnit = distanceUnit,
                onClick = { },
                onExpandedStateChange = { expanded ->
                    onExpandedStateChange(workoutExerciseComponent.workoutExerciseId, expanded)
                },
                onExerciseGifClick = onExerciseGifClick,
            )
        }

        item {
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}