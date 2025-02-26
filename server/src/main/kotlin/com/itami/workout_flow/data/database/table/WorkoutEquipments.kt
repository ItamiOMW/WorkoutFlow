package com.itami.workout_flow_ktor.data.database.table

import com.itami.workout_flow.data.database.table.Workouts
import com.itami.workout_flow.model.Equipment
import org.jetbrains.exposed.dao.id.CompositeIdTable

object WorkoutEquipments: CompositeIdTable("workout_equipments") {
    val workoutId = reference("workout_id", Workouts.id)
    val equipment = enumeration<Equipment>("equipment").entityId()

    init {
        addIdColumn(workoutId)
    }

    override val primaryKey = PrimaryKey(workoutId, equipment)
}