package com.itami.workout_flow.data.repository.exercise

import com.itami.workout_flow.data.database.DatabaseFactory.dbQuery
import com.itami.workout_flow.data.database.entity.ExerciseEntity
import com.itami.workout_flow.data.database.table.ExerciseEquipments
import com.itami.workout_flow.data.database.table.ExerciseFavorites
import com.itami.workout_flow.data.database.table.ExerciseMuscleInvolvements
import com.itami.workout_flow.data.database.table.ExerciseSteps
import com.itami.workout_flow.data.database.table.Exercises
import com.itami.workout_flow.data.mapper.toExercise
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Exercise
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.Muscle
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.trim

class ExerciseRepositoryImpl : ExerciseRepository {

    override suspend fun getExercises(
        userId: Long?,
        query: String,
        page: Int,
        pageSize: Int,
        exerciseTypes: List<ExerciseType>,
        equipments: List<Equipment>,
        muscles: List<Muscle>
    ): List<Exercise> {
        return dbQuery {
            Exercises
                .leftJoin(ExerciseSteps)
                .leftJoin(ExerciseEquipments)
                .leftJoin(ExerciseMuscleInvolvements)
                .leftJoin(ExerciseFavorites)
                .selectAll()
                .apply {
                    andWhere { (Exercises.name.trim().lowerCase() like "%$query%") }

                    if (exerciseTypes.isNotEmpty()) {
                        andWhere { Exercises.exerciseType inList exerciseTypes }
                    }

                    if (equipments.isNotEmpty()) {
                        andWhere { ExerciseEquipments.equipment inList equipments }
                    }

                    if (muscles.isNotEmpty()) {
                        andWhere { ExerciseMuscleInvolvements.muscle inList muscles }
                    }
                }
                .limit(n = pageSize, offset = ((page - 1) * pageSize).toLong())
                .map { resultRow ->
                    ExerciseEntity.wrapRow(resultRow).toExercise()
                }
        }
    }

}