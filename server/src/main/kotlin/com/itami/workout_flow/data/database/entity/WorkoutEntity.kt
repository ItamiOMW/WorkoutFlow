package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.WorkoutExercises
import com.itami.workout_flow.data.database.table.WorkoutFavorites
import com.itami.workout_flow.data.database.table.WorkoutMuscles
import com.itami.workout_flow.data.database.table.WorkoutTypes
import com.itami.workout_flow.data.database.table.Workouts
import com.itami.workout_flow_ktor.data.database.table.*
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class WorkoutEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<WorkoutEntity>(Workouts)

    val clientUUID by Workouts.clientUUID
    val author by UserEntity referencedOn Workouts.authorId
    val name by Workouts.name
    val description by Workouts.description
    val durationMin by Workouts.durationMin
    val exercises by WorkoutExerciseEntity referrersOn WorkoutExercises.workoutId
    val types by WorkoutTypeEntity referrersOn WorkoutTypes.workoutId
    val muscles by WorkoutMuscleEntity referrersOn WorkoutMuscles.workoutId
    val favorites by WorkoutFavoriteEntity referrersOn WorkoutFavorites.workoutId
    val equipments by WorkoutEquipmentEntity referrersOn WorkoutEquipments.workoutId
    val isPublic by Workouts.isPublic
    val createdAt by Workouts.createdAt
    val updatedAt by Workouts.updatedAt
}