package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.WorkoutFavorites
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class WorkoutFavoriteEntity(id: EntityID<CompositeID>): CompositeEntity(id) {
    companion object : CompositeEntityClass<WorkoutFavoriteEntity>(WorkoutFavorites)

    val workoutId by WorkoutFavorites.workoutId
    val userId by WorkoutFavorites.userId
    val isFavorite by WorkoutFavorites.isFavorite
    val isFavoriteChangedAt by WorkoutFavorites.isFavoriteChangedAt
}