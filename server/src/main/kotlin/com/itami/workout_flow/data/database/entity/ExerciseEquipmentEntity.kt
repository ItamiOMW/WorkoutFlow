package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.ExerciseEquipments
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class ExerciseEquipmentEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object : CompositeEntityClass<ExerciseEquipmentEntity>(ExerciseEquipments)

    val exerciseId by ExerciseEquipments.exerciseId
    val equipment by ExerciseEquipments.equipment
}