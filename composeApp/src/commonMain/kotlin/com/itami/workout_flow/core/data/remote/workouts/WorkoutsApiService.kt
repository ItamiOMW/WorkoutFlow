package com.itami.workout_flow.core.data.remote.workouts

import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.dto.response.WorkoutsResponse
import com.itami.workout_flow.model.WorkoutsFilter
import com.itami.workout_flow.model.WorkoutsSort

interface WorkoutsApiService {

    suspend fun getWorkouts(
        page: Int,
        pageSize: Int,
        query: String,
        workoutsFilter: WorkoutsFilter,
        workoutsSort: WorkoutsSort
    ): AppResult<WorkoutsResponse, DataError.Remote>

}