package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.WorkoutTypes
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class WorkoutTypeEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object : CompositeEntityClass<WorkoutTypeEntity>(WorkoutTypes)

    val workoutId by WorkoutTypes.workoutId
    val workoutType by WorkoutTypes.workoutType
}