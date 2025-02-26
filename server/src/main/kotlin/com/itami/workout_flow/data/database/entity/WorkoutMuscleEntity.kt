package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.WorkoutMuscles
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class WorkoutMuscleEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object : CompositeEntityClass<WorkoutMuscleEntity>(WorkoutMuscles)

    val workoutId by WorkoutMuscles.workoutId
    val muscle by WorkoutMuscles.muscle
}