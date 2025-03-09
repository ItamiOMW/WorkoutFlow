package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.ExerciseEquipments
import com.itami.workout_flow.data.database.table.ExerciseFavorites
import com.itami.workout_flow.data.database.table.ExerciseMuscleInvolvements
import com.itami.workout_flow.data.database.table.ExerciseSteps
import com.itami.workout_flow.data.database.table.Exercises
import com.itami.workout_flow_ktor.data.database.table.*
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ExerciseEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<ExerciseEntity>(Exercises)

    val name by Exercises.name
    val description by Exercises.description
    val exerciseType by Exercises.exerciseType
    val exerciseGifUrl by Exercises.exerciseGifUrl
    val muscleInvolvements by ExerciseMuscleInvolvementEntity referrersOn ExerciseMuscleInvolvements.exerciseId
    val exerciseFavorites by ExerciseFavoriteEntity referrersOn ExerciseFavorites.exerciseId
    val equipments by ExerciseEquipmentEntity referrersOn ExerciseEquipments.exerciseId
    val steps by ExerciseStepEntity referrersOn ExerciseSteps.exerciseId orderBy ExerciseSteps.order
}