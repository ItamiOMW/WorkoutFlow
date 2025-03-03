package com.itami.workout_flow.data.database.table

import com.itami.workout_flow.model.ExerciseType
import org.jetbrains.exposed.dao.id.LongIdTable

object Exercises: LongIdTable("exercises") {
    val name = varchar("name", 100)
    val description = varchar("description", 200).nullable()
    val exerciseType = enumeration<ExerciseType>("exercise_type")
    val exerciseGifUrl = varchar("exercise_gif_url", 255).nullable()
}