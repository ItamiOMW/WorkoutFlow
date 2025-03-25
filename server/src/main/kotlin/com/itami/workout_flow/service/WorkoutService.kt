package com.itami.workout_flow.service

import com.itami.workout_flow.data.mapper.toExerciseResponse
import com.itami.workout_flow.data.mapper.toUserProfileResponse
import com.itami.workout_flow.data.mapper.toWorkoutResponse
import com.itami.workout_flow.data.repository.workout.WorkoutRepository
import com.itami.workout_flow.dto.response.WorkoutsResponse
import com.itami.workout_flow.model.WorkoutsSort
import com.itami.workout_flow.routes.WorkoutsRoute
import kotlinx.datetime.toJavaInstant
import java.time.ZoneOffset

class WorkoutService(private val workoutRepository: WorkoutRepository) {

    suspend fun getWorkouts(
        userId: Long?,
        workoutsRoute: WorkoutsRoute,
    ): WorkoutsResponse {
        val workouts = workoutRepository.getWorkouts(
            userId = userId,
            query = workoutsRoute.query ?: "",
            page = workoutsRoute.page ?: 1,
            pageSize = workoutsRoute.pageSize ?: 10,
            workoutsSort = workoutsRoute.sort ?: WorkoutsSort.Newest,
            workoutTypes = workoutsRoute.types ?: emptyList(),
            equipments = workoutsRoute.equipment ?: emptyList(),
            muscles = workoutsRoute.muscles ?: emptyList(),
            timeFilters = workoutsRoute.timeFilters ?: emptyList(),
            createdBeforeCursor = workoutsRoute.createdBeforeCursor.toJavaInstant().atOffset(ZoneOffset.UTC)
        )

        val exercisesResponses = workouts
            .flatMap { workout -> workout.workoutExercises.map { it.exercise.toExerciseResponse() } }
            .distinctBy { it.id }

        val authorsResponses = workouts
            .map { workout -> workout.author.toUserProfileResponse() }
            .distinctBy { it.id }

        val workoutsResponses = workouts.map { it.toWorkoutResponse() }

        val workoutsResponse = WorkoutsResponse(
            exercises = exercisesResponses,
            authors = authorsResponses,
            workouts = workoutsResponses
        )
        return workoutsResponse
    }

    suspend fun getPopularWorkouts(limit: Int, userId: Long?): WorkoutsResponse {
        val workouts = workoutRepository.getPopularWorkouts(limit, userId)
        val exercisesResponses = workouts
            .flatMap { workout -> workout.workoutExercises.map { it.exercise.toExerciseResponse() } }
            .distinctBy { it.id }

        val authorsResponses = workouts
            .map { workout -> workout.author.toUserProfileResponse() }
            .distinctBy { it.id }

        val workoutsResponses = workouts.map { it.toWorkoutResponse() }

        val workoutsResponse = WorkoutsResponse(
            exercises = exercisesResponses,
            authors = authorsResponses,
            workouts = workoutsResponses
        )
        return workoutsResponse
    }

}