package com.itami.workout_flow.data.database.entity

import com.itami.workout_flow.data.database.table.Sets
import com.itami.workout_flow.data.database.table.WorkoutExercises
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class WorkoutExerciseEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<WorkoutExerciseEntity>(WorkoutExercises)

    val clientUUID by WorkoutExercises.clientUUID
    val workoutId by WorkoutExercises.workoutId
    val exercise by ExerciseEntity referencedOn WorkoutExercises.exerciseId
    val sets by SetEntity referrersOn Sets.workoutExerciseId
    val supersetId by WorkoutExercises.supersetId
    val order by WorkoutExercises.order
}