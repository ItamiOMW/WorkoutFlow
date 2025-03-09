package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.ExerciseSteps
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class ExerciseStepEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object : CompositeEntityClass<ExerciseStepEntity>(ExerciseSteps)

    val exerciseId by ExerciseSteps.exerciseId
    val stepText by ExerciseSteps.stepText
    val order by ExerciseSteps.order
}