package com.itami.workout_flow.core.data.mapper

import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseEntity
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseEquipmentEntity
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseMuscleInvolvementEntity
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseWithDetails
import com.itami.workout_flow.core.data.local.database.entity.workout.SetEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.SupersetEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutEntry
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutEquipmentEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutExerciseEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutExerciseEntry
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutExerciseWithDetails
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutMuscleEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutPreviewDbView
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutTypeEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutWithDetails
import com.itami.workout_flow.core.domain.model.workout.Set
import com.itami.workout_flow.core.domain.model.workout.Workout
import com.itami.workout_flow.core.domain.model.workout.WorkoutExercise
import com.itami.workout_flow.core.domain.model.workout.WorkoutPreview
import com.itami.workout_flow.dto.response.ExerciseResponse
import com.itami.workout_flow.dto.response.WorkoutExerciseResponse
import com.itami.workout_flow.dto.response.WorkoutResponse
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.model.MuscleInvolvement
import kotlinx.datetime.Instant

fun WorkoutPreviewDbView.toWorkoutPreview() = WorkoutPreview(
    id = this.clientUUID,
    author = this.authorProfileEntity.toUserProfile(),
    name = this.name,
    durationMin = this.durationMin,
    muscles = this.workoutMuscles.map { it.muscle },
    favoritesCount = this.favoritesCount,
    isFavorite = this.isFavorite,
    isCreatedByCurrentUser = this.isCreatedByCurrentUser
)

fun WorkoutResponse.toWorkoutPreview() = WorkoutPreview(
    id = this.clientUUID,
    author = null,
    name = this.name,
    durationMin = this.durationMin,
    muscles = this.muscles,
    favoritesCount = this.favoritesCount,
    isFavorite = this.isFavorite,
    isCreatedByCurrentUser = this.isCreatedByUser
)

fun WorkoutWithDetails.toWorkout() = Workout(
    id = this.workout.clientUUID,
    author = this.author?.toUserProfile(),
    name = this.workout.name,
    description = this.workout.description,
    durationMin = this.workout.durationMin,
    workoutTypes = this.workoutTypes.map { it.workoutType },
    equipments = this.workoutEquipments.map { it.equipment },
    muscles = this.workoutMuscles.map { it.muscle },
    workoutExercises = this.workoutExerciseDetails.map { it.toWorkoutExercise() },
    favoritesCount = this.workout.favoritesCount,
    isFavorite = this.workout.isFavorite,
    isPublic = this.workout.isPublic,
    isCreatedByCurrentUser = this.workout.isCreatedByCurrentUser,
    createdAt = this.workout.createdAt
)

fun WorkoutExerciseWithDetails.toWorkoutExercise() = WorkoutExercise(
    id = this.workoutExercise.clientUUID,
    workoutId = this.workoutExercise.workoutUUID,
    supersetId = this.superset?.clientUUID,
    exercise = this.exerciseWithDetails.toExercise(),
    sets = this.sets.map {
        Set(
            id = it.clientUUID,
            workoutExerciseId = it.workoutExerciseUUID,
            reps = it.reps,
            weightGrams = it.weightGrams,
            distanceMeters = it.distanceMeters,
            timeSeconds = it.timeSeconds,
            order = it.order
        )
    },
    order = this.workoutExercise.order
)

fun ExerciseWithDetails.toExercise() = Exercise(
    id = this.exercise.id,
    name = this.exercise.name,
    exerciseGifUrl = this.exercise.exerciseGifUrl,
    exerciseType = this.exercise.exerciseType,
    steps = this.exercise.steps,
    muscleInvolvements = this.muscleInvolvements.map {
        MuscleInvolvement(
            muscleRole = it.muscleRole,
            muscle = it.muscle,
            exerciseId = this.exercise.id
        )
    },
    equipments = this.equipments.map { it.equipment },
    description = this.exercise.description
)

fun WorkoutResponse.toWorkoutEntry() = WorkoutEntry(
    workout = WorkoutEntity(
        clientUUID = this.clientUUID,
        serverId = this.serverId,
        authorId = this.authorId,
        name = this.name,
        description = this.description,
        durationMin = this.durationMin,
        favoritesCount = this.favoritesCount,
        isFavorite = this.isFavorite,
        isPublic = this.isPublic,
        isCreatedByCurrentUser = this.isCreatedByUser,
        createdAt = Instant.parse(this.createdAt),
        updatedAt = this.updatedAt?.let { Instant.parse(it) },
        isFavoriteChangedAt = this.isFavoriteChangedAt?.let { Instant.parse(it) }
    ),
    workoutTypes = this.workoutTypes.map { type ->
        WorkoutTypeEntity(
            workoutUUID = this.clientUUID,
            workoutType = type
        )
    },
    workoutMuscles = this.muscles.map { muscle ->
        WorkoutMuscleEntity(
            workoutUUID = this.clientUUID,
            muscle = muscle
        )
    },
    workoutEquipments = this.equipments.map { equipment ->
        WorkoutEquipmentEntity(
            workoutUUID = this.clientUUID,
            equipment = equipment
        )
    },
    workoutExerciseEntries = this.workoutExercises.map {
        it.toWorkoutExerciseEntry(workoutId = this.clientUUID)
    },
)

fun WorkoutExerciseResponse.toWorkoutExerciseEntry(
    workoutId: String,
) = WorkoutExerciseEntry(
    workoutExercise = WorkoutExerciseEntity(
        clientUUID = this.clientUUID,
        serverId = this.serverId,
        workoutUUID = workoutId,
        exerciseId = this.exerciseId,
        supersetUUID = this.supersetClientUUID,
        order = this.order,
    ),
    superset = if (supersetClientUUID != null && supersetServerId != null) {
        SupersetEntity(
            clientUUID = supersetClientUUID!!,
            serverId = supersetServerId,
            workoutUUID = workoutId
        )
    } else {
        null
    },
    sets = this.sets.map { set ->
        SetEntity(
            clientUUID = set.clientUUID,
            serverId = set.serverId,
            workoutExerciseUUID = this.clientUUID,
            reps = set.reps,
            weightGrams = set.weightGrams,
            distanceMeters = set.distanceMeters,
            timeSeconds = set.timeSeconds,
            order = set.order
        )
    }
)

fun ExerciseResponse.toExerciseWithDetails() = ExerciseWithDetails(
    exercise = ExerciseEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        steps = this.steps,
        exerciseGifUrl = exerciseGifUrl,
        exerciseType = this.exerciseType,
    ),
    muscleInvolvements = this.muscleInvolvements.map { muscleInvolvement ->
        ExerciseMuscleInvolvementEntity(
            muscle = muscleInvolvement.muscle,
            muscleRole = muscleInvolvement.muscleRole,
            exerciseId = muscleInvolvement.exerciseId
        )
    },
    equipments = this.equipments.map { equipment ->
        ExerciseEquipmentEntity(
            exerciseId = this.id,
            equipment = equipment
        )
    }
)