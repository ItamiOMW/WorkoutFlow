package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.Supersets
import com.itami.workout_flow.data.database.table.WorkoutExercises
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SupersetEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<SupersetEntity>(Supersets)

    val clientUUID by Supersets.clientUUID
    val workoutId by Supersets.workoutId
    val exercises by WorkoutExerciseEntity optionalReferrersOn WorkoutExercises.supersetId
}