package com.itami.workout_flow.core.data.repository

import com.itami.workout_flow.core.domain.model.workout.ScheduledWorkout
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultWorkoutRepository : WorkoutRepository {

    override fun getScheduledWorkouts(): Flow<List<ScheduledWorkout>> {

        return flow {  }
    }

}
