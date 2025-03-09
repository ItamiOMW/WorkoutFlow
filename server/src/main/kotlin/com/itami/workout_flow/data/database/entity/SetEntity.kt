package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.Sets
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SetEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<SetEntity>(Sets)

    val clientUUID by Sets.clientUUID
    val workoutExerciseId by Sets.workoutExerciseId
    val reps by Sets.reps
    val weightGrams by Sets.weightGrams
    val distanceMeters by Sets.distanceMeters
    val timeSeconds by Sets.timeMillis
    val order by Sets.order
}