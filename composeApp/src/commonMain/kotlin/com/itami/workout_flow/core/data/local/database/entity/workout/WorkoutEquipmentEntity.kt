package com.itami.workout_flow.core.data.local.database.entity.workout

import androidx.room.Entity
import androidx.room.ForeignKey
import com.itami.workout_flow.model.Equipment

@Entity(
    tableName = "workout_equipments",
    primaryKeys = ["workoutUUID", "equipment"],
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["clientUUID"],
            childColumns = ["workoutUUID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutEquipmentEntity(
    val workoutUUID: String,
    val equipment: Equipment,
)