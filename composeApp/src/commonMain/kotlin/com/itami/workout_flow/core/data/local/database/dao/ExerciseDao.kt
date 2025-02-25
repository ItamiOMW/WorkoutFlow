package com.itami.workout_flow.core.data.local.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseEntity
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseEquipmentEntity
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseMuscleInvolvementEntity
import com.itami.workout_flow.core.data.local.database.entity.exercise.ExerciseWithDetails
import kotlinx.coroutines.flow.Flow

interface ExerciseDao {

    @Transaction
    @Query("SELECT * FROM exercises")
    fun getAllExerciseWithDetailsFlow(): Flow<List<ExerciseWithDetails>>

    @Transaction
    @Query("SELECT * FROM exercises WHERE id = :exerciseId LIMIT 1")
    fun getExerciseWithDetailsByIdFlow(exerciseId: Long): Flow<ExerciseWithDetails>

    @Transaction
    @Query("SELECT * FROM exercises WHERE id = :exerciseId LIMIT 1")
    suspend fun getExerciseWithDetailsById(exerciseId: Long): ExerciseWithDetails

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: ExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<ExerciseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseEquipments(exerciseEquipments: List<ExerciseEquipmentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseMuscleInvolvements(exerciseMuscleInvolvements: List<ExerciseMuscleInvolvementEntity>)

    @Query("DELETE FROM exercises WHERE id = :userId")
    suspend fun delete(userId: Long)

    @Query("DELETE FROM exercises")
    suspend fun deleteAll()

    @Transaction
    suspend fun insertExerciseWithDetails(
        exercise: ExerciseEntity,
        exerciseEquipments: List<ExerciseEquipmentEntity>,
        exerciseMuscleInvolvements: List<ExerciseMuscleInvolvementEntity>
    ) {
        insert(exercise)
        insertExerciseEquipments(exerciseEquipments)
        insertExerciseMuscleInvolvements(exerciseMuscleInvolvements)
    }

}