package com.itami.workout_flow.core.data.local.database.entity.exercise

import androidx.room.Entity
import androidx.room.ForeignKey
import com.itami.workout_flow.model.Equipment

@Entity(
    tableName = "exercise_equipments",
    primaryKeys = ["exerciseId", "equipment"],
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseEquipmentEntity(
    val exerciseId: Long,
    val equipment: Equipment,
)