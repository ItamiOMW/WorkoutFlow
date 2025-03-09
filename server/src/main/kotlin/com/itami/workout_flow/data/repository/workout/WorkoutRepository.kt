package com.itami.workout_flow.data.repository.workout

import com.itami.workout_flow.data.model.workout.Workout
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutTimeFilter
import com.itami.workout_flow.model.WorkoutType
import com.itami.workout_flow.model.WorkoutsSort
import java.time.OffsetDateTime

interface WorkoutRepository {

    suspend fun getWorkouts(
        userId: Long?,
        query: String,
        page: Int,
        pageSize: Int,
        createdBeforeCursor: OffsetDateTime,
        workoutsSort: WorkoutsSort,
        workoutTypes: List<WorkoutType>,
        equipments: List<Equipment>,
        muscles: List<Muscle>,
        timeFilters: List<WorkoutTimeFilter>,
    ): List<Workout>

    suspend fun getPopularWorkouts(limit: Int, userId: Long?): List<Workout>

}