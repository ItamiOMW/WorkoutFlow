package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow_ktor.data.database.table.WorkoutEquipments
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class WorkoutEquipmentEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object : CompositeEntityClass<WorkoutEquipmentEntity>(WorkoutEquipments)

    val workoutId by WorkoutEquipments.workoutId
    val equipment by WorkoutEquipments.equipment
}