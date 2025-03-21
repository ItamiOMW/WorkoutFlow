package com.itami.workout_flow.core.data.remote.exercises

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import app.cash.paging.RemoteMediator
import com.itami.workout_flow.core.data.local.database.WorkoutFlowDatabase
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseWithDetails
import com.itami.workout_flow.core.data.mapper.toExerciseWithDetails
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.Muscle

@OptIn(ExperimentalPagingApi::class)
class ExerciseRemoteMediator(
    private val database: WorkoutFlowDatabase,
    private val exercisesApiService: ExercisesApiService,
    private val pageSize: Int,
    private val query: String?,
    private val muscles: List<Muscle>,
    private val exerciseTypes: List<ExerciseType>,
    private val equipments: List<Equipment>,
) : RemoteMediator<Int, ExerciseWithDetails>() {

    private var page: Int = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ExerciseWithDetails>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    page = 1
                    page
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    page++
                    page
                }
            }

            val response = exercisesApiService.getExercises(
                page = loadKey,
                pageSize = pageSize,
                query = query,
                muscles = muscles,
                exerciseTypes = exerciseTypes,
            )

            return when (response) {
                is AppResult.Success -> {
                    val exerciseResponses = response.data
                    val exercisesWithDetails = exerciseResponses.map { it.toExerciseWithDetails() }
                    exercisesWithDetails.forEach { exerciseWithDetails ->
                        database.exerciseDao.insertExerciseWithDetails(exerciseWithDetails)
                    }
                    MediatorResult.Success(endOfPaginationReached = exerciseResponses.isEmpty())
                }

                is AppResult.Error -> {
                    MediatorResult.Error(response.error)
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}