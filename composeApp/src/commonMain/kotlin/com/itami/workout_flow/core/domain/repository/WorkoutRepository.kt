package com.itami.workout_flow.core.domain.repository

import com.itami.workout_flow.core.domain.model.workout.ScheduledWorkout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    fun getScheduledWorkouts(): Flow<List<ScheduledWorkout>>

}