package com.itami.workout_flow.data.database.table

import com.itami.workout_flow.model.Equipment
import org.jetbrains.exposed.dao.id.CompositeIdTable

object ExerciseEquipments: CompositeIdTable("exercise_equipments") {
    val exerciseId = reference("exercise_id", Exercises.id)
    val equipment = enumeration<Equipment>("equipment").entityId()

    init {
        addIdColumn(exerciseId)
    }

    override val primaryKey = PrimaryKey(exerciseId, equipment)
}