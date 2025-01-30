package com.itami.workout_flow.core.data.repository

import com.itami.workout_flow.core.domain.model.user.UserProfile
import com.itami.workout_flow.core.domain.model.workout.ScheduledWorkout
import com.itami.workout_flow.core.domain.model.workout.WorkoutPreview
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import com.itami.workout_flow.model.DayOfWeek
import com.itami.workout_flow.model.Muscle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockWorkoutRepository : WorkoutRepository {

    override fun getScheduledWorkouts(): Flow<List<ScheduledWorkout>> {
        return flow {
            emit(
                listOf(
                    ScheduledWorkout(
                        id = "1",
                        workoutPreview = WorkoutPreview(
                            id = "1",
                            author = UserProfile(
                                id = 1,
                                name = "Itami",
                                username = "itamiomw",
                                profilePictureUrl = "https://i.pinimg.com/736x/6e/9e/dc/6e9edc603eabce0b383680797ca59b74.jpg",
                                subscription = UserProfile.Subscription.PREMIUM,
                            ),
                            name = "Fullbody Strength",
                            durationMin = 60,
                            muscles = listOf(Muscle.Fullbody),
                            favoritesCount = 53,
                            isFavorite = true,
                            isCreatedByCurrentUser = true
                        ),
                        dayOfWeek = DayOfWeek.MONDAY
                    ),
                    ScheduledWorkout(
                        id = "2",
                        workoutPreview = WorkoutPreview(
                            id = "2",
                            author = UserProfile(
                                id = 2,
                                name = "Tackake",
                                username = "tackakeomw",
                                profilePictureUrl = "https://i.pinimg.com/736x/a7/51/f0/a751f0af67fbb8fe579f2ef52cfed693.jpg",
                                subscription = UserProfile.Subscription.BASIC,
                            ),
                            name = "Arms & Shoulders",
                            durationMin = 40,
                            muscles = listOf(
                                Muscle.FrontDelt,
                                Muscle.MiddleDelt,
                                Muscle.RearDelt,
                                Muscle.Biceps,
                                Muscle.Triceps,
                                Muscle.Forearms
                            ),
                            favoritesCount = 104,
                            isFavorite = false,
                            isCreatedByCurrentUser = false
                        ),
                        dayOfWeek = DayOfWeek.FRIDAY
                    )
                )
            )
        }
    }

}