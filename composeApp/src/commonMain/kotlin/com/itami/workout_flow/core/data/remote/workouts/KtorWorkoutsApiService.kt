package com.itami.workout_flow.core.data.remote.workouts

import com.itami.workout_flow.core.data.remote.utils.safeRequest
import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.dto.response.WorkoutsResponse
import com.itami.workout_flow.model.WorkoutsFilter
import com.itami.workout_flow.model.WorkoutsSort
import com.itami.workout_flow.routes.WorkoutsRoute
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get

class KtorWorkoutsApiService(private val httpClient: HttpClient) : WorkoutsApiService {

    override suspend fun getWorkouts(
        lastItemId: Long?,
        pageSize: Int,
        query: String?,
        workoutsFilter: WorkoutsFilter,
        workoutsSort: WorkoutsSort
    ): AppResult<WorkoutsResponse, DataError.Remote> {
        return safeRequest {
            httpClient.get(
                WorkoutsRoute(
                    lastItemId = lastItemId,
                    pageSize = pageSize,
                    query = query,
                    types = workoutsFilter.selectedWorkoutTypes,
                    equipment = workoutsFilter.selectedEquipments,
                    muscles = workoutsFilter.selectedMuscles,
                    timeFilters = workoutsFilter.selectedTimeFilters,
                    sort = workoutsSort,
                )
            )
        }
    }

}