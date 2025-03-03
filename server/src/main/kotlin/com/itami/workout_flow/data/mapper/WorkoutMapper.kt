package com.itami.workout_flow.data.mapper

import com.itami.workout_flow.data.database.entity.ExerciseEntity
import com.itami.workout_flow.data.database.entity.WorkoutEntity
import com.itami.workout_flow.data.database.entity.WorkoutExerciseEntity
import com.itami.workout_flow.data.model.workout.Set
import com.itami.workout_flow.data.model.workout.Workout
import com.itami.workout_flow.data.model.workout.WorkoutExercise
import com.itami.workout_flow.dto.response.ExerciseResponse
import com.itami.workout_flow.dto.response.ExerciseStepResponse
import com.itami.workout_flow.dto.response.MuscleInvolvementResponse
import com.itami.workout_flow.dto.response.SetResponse
import com.itami.workout_flow.dto.response.WorkoutExerciseResponse
import com.itami.workout_flow.dto.response.WorkoutResponse
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.model.ExerciseStep
import com.itami.workout_flow.model.MuscleInvolvement

fun WorkoutEntity.toWorkout(userId: Long?): Workout {
    val favoriteEntity = this.favorites.find { it.userId.value == userId}
    return Workout(
        serverId = this.id.value,
        clientUUID = this.clientUUID.toString(),
        author = this.author.toUser(),
        name = this.name,
        description = this.description,
        durationMin = this.durationMin,
        workoutTypes = this.types.map { it.workoutType.value },
        equipments = this.equipments.map { it.equipment.value },
        muscles = this.muscles.map { it.muscle.value },
        workoutExercises = this.exercises.map { it.toWorkoutExercise() },
        favoritesCount = this.favorites.count { it.isFavorite },
        isFavorite = favoriteEntity?.isFavorite == true,
        isPublic = this.isPublic,
        isCreatedByUser = this.author.id.value == userId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        isFavoriteChangedAt = favoriteEntity?.isFavoriteChangedAt
    )
}

fun WorkoutExerciseEntity.toWorkoutExercise() = WorkoutExercise(
    serverId = this.id.value,
    clientUUID = this.clientUUID.toString(),
    supersetId = this.supersetId?.value,
    supersetUUID = this.supersetId?.value?.toString(),
    exercise = this.exercise.toExercise(),
    sets = this.sets.map {
        Set(
            serverId = it.id.value,
            clientUUID = it.clientUUID.toString(),
            workoutExerciseId = it.workoutExerciseId.value,
            reps = it.reps,
            weightGrams = it.weightGrams,
            distanceMeters = it.distanceMeters,
            timeSeconds = it.timeSeconds,
            order = it.order
        )
    },
    order = this.order,
)

fun ExerciseEntity.toExercise() = Exercise(
    id = this.id.value,
    name = this.name,
    description = this.description,
    exerciseGifUrl = this.exerciseGifUrl,
    exerciseType = this.exerciseType,
    steps = this.steps.map {
        ExerciseStep(
            exerciseId = it.exerciseId.value,
            text = it.stepText,
            order = it.order.value
        )
    },
    muscleInvolvements = this.muscleInvolvements.map {
        MuscleInvolvement(
            exerciseId = it.exerciseId.value,
            muscle = it.muscle.value,
            muscleRole = it.muscleRole.value
        )
    },
    equipments = this.equipments.map { it.equipment.value }
)

fun Workout.toWorkoutResponse() = WorkoutResponse(
    serverId = this.serverId,
    clientUUID = this.clientUUID,
    authorId = this.author.id,
    name = this.name,
    description = this.description,
    durationMin = this.durationMin,
    workoutTypes = this.workoutTypes,
    equipments = this.equipments,
    muscles = this.muscles,
    workoutExercises = this.workoutExercises.map { it.toWorkoutExerciseResponse() },
    favoritesCount = this.favoritesCount,
    isFavorite = this.isFavorite,
    isPublic = this.isPublic,
    isCreatedByUser = this.isCreatedByUser,
    createdAt = this.createdAt.toString(),
    updatedAt = this.updatedAt?.toString(),
    isFavoriteChangedAt = this.isFavoriteChangedAt?.toString()
)

fun WorkoutExercise.toWorkoutExerciseResponse() = WorkoutExerciseResponse(
    serverId = this.serverId,
    clientUUID = this.clientUUID,
    supersetServerId = this.supersetId,
    supersetClientUUID = this.supersetUUID,
    exerciseId = this.exercise.id,
    sets = this.sets.map {
        SetResponse(
            serverId = it.serverId,
            clientUUID = it.clientUUID,
            reps = it.reps,
            weightGrams = it.weightGrams,
            distanceMeters = it.distanceMeters,
            timeSeconds = it.timeSeconds,
            order = it.order
        )
    },
    order = this.order
)

fun Exercise.toExerciseResponse() = ExerciseResponse(
    id = this.id,
    name = this.name,
    description = this.description,
    exerciseGifUrl = this.exerciseGifUrl,
    exerciseType = this.exerciseType,
    steps = this.steps.map {
        ExerciseStepResponse(
            exerciseId = it.exerciseId,
            text = it.text,
            order = it.order
        )
    },
    muscleInvolvements = this.muscleInvolvements.map {
        MuscleInvolvementResponse(
            exerciseId = it.exerciseId,
            muscle = it.muscle,
            muscleRole = it.muscleRole
        )
    },
    equipments = this.equipments,
)