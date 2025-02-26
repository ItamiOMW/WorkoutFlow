package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.ExerciseFavorites
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class ExerciseFavoriteEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object : CompositeEntityClass<ExerciseFavoriteEntity>(ExerciseFavorites)

    val exerciseId by ExerciseFavorites.exerciseId
    val userId by ExerciseFavorites.userId
}