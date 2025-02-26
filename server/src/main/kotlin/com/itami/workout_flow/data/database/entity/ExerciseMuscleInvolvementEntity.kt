package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.ExerciseMuscleInvolvements
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class ExerciseMuscleInvolvementEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object : CompositeEntityClass<ExerciseMuscleInvolvementEntity>(ExerciseMuscleInvolvements)

    val exerciseId by ExerciseMuscleInvolvements.exerciseId
    val muscle by ExerciseMuscleInvolvements.muscle
    val muscleRole by ExerciseMuscleInvolvements.muscleRole
}