package com.itami.workout_flow.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.RoomRawQuery
import androidx.room.Transaction
import androidx.room.Update
import app.cash.paging.PagingSource
import com.itami.workout_flow.core.data.local.database.entity.user.UserProfileEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.ScheduledWorkoutWithWorkoutDetails
import com.itami.workout_flow.core.data.local.database.entity.workout.SetEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.SupersetEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutEntry
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutEquipmentEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutExerciseEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutMuscleEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutPreviewDbView
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutTypeEntity
import com.itami.workout_flow.core.data.local.database.entity.workout.WorkoutWithDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

@Dao
interface WorkoutDao {

    @Transaction
    @RawQuery(
        observedEntities = [
            WorkoutEntity::class,
            WorkoutMuscleEntity::class,
            WorkoutEquipmentEntity::class,
            WorkoutTypeEntity::class,
            UserProfileEntity::class
        ]
    )
    fun getWorkoutsPreviewsPagingSource(sqlQuery: RoomRawQuery): PagingSource<Int, WorkoutPreviewDbView>

    @Transaction
    @RawQuery(
        observedEntities = [
            WorkoutEntity::class,
            WorkoutMuscleEntity::class,
            WorkoutEquipmentEntity::class,
            WorkoutTypeEntity::class,
            UserProfileEntity::class
        ]
    )
    fun observeWorkoutsPreviews(sqlQuery: RoomRawQuery): Flow<List<WorkoutPreviewDbView>>

    @Query("SELECT * FROM workouts WHERE clientUUID = :uuid LIMIT 1")
    suspend fun getWorkoutByUUID(uuid: String): WorkoutEntity?

    @Query("SELECT * FROM workouts WHERE serverId = :serverId LIMIT 1")
    suspend fun getWorkoutByServerId(serverId: Long): WorkoutEntity?

    @Query("SELECT * FROM workouts WHERE clientUUID = :uuid LIMIT 1")
    suspend fun getWorkoutWithDetailsByUUID(uuid: String): WorkoutWithDetails?

    @Transaction
    @Query("SELECT * FROM workouts WHERE clientUUID = :uuid LIMIT 1")
    fun observeWorkoutWithDetailsByUUID(uuid: String): Flow<WorkoutWithDetails?>

    @Transaction
    @Query("SELECT * FROM scheduled_workouts")
    fun observeScheduledWorkoutsPreviews(): Flow<List<ScheduledWorkoutWithWorkoutDetails>>

    @Transaction
    @Query("SELECT * FROM scheduled_workouts WHERE dayOfWeek = :dayOfWeek")
    fun observeScheduledWorkoutsByDay(dayOfWeek: Int): Flow<List<ScheduledWorkoutWithWorkoutDetails>>

    @Query("SELECT isFavorite FROM workouts WHERE serverId = :serverId LIMIT 1")
    suspend fun isWorkoutFavorite(serverId: Long): Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWorkouts(workouts: List<WorkoutEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Query("UPDATE workouts SET isFavorite = :isFavorite, isFavoriteChangedAt = :isFavoriteChangedAt WHERE clientUUID = :workoutUUID")
    suspend fun setFavorite(
        workoutUUID: String,
        isFavorite: Boolean,
        isFavoriteChangedAt: Instant,
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutEquipments(workoutEquipments: List<WorkoutEquipmentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutMuscles(workoutsMuscles: List<WorkoutMuscleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutTypes(workoutsTypes: List<WorkoutTypeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutExercises(workoutsExercises: List<WorkoutExerciseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupersets(supersets: List<SupersetEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(sets: List<SetEntity>)

    @Query("DELETE FROM workouts WHERE clientUUID = :workoutUUID")
    suspend fun deleteWorkout(workoutUUID: String)

    @Query("DELETE FROM workouts")
    suspend fun deleteAllWorkouts()

    @Query("DELETE FROM workout_types WHERE workoutUUID = :workoutUUID")
    suspend fun deleteWorkoutTypes(workoutUUID: String)

    @Query("DELETE FROM workout_muscles WHERE workoutUUID = :workoutUUID")
    suspend fun deleteWorkoutMuscles(workoutUUID: String)

    @Query("DELETE FROM workout_equipments WHERE workoutUUID = :workoutUUID")
    suspend fun deleteWorkoutEquipments(workoutUUID: String)

    @Query("DELETE FROM workout_exercises WHERE workoutUUID = :workoutUUID")
    suspend fun deleteWorkoutExercises(workoutUUID: String)

    @Transaction
    suspend fun insertWorkoutWithDetails(
        workout: WorkoutEntity,
        workoutEquipments: List<WorkoutEquipmentEntity>,
        workoutsMuscles: List<WorkoutMuscleEntity>,
        workoutsTypes: List<WorkoutTypeEntity>,
        workoutsExercises: List<WorkoutExerciseEntity>,
        supersets: List<SupersetEntity>,
        sets: List<SetEntity>,
    ) {
        insertWorkout(workout)
        insertWorkoutEquipments(workoutEquipments)
        insertWorkoutMuscles(workoutsMuscles)
        insertWorkoutTypes(workoutsTypes)
        insertSupersets(supersets)
        insertWorkoutExercises(workoutsExercises)
        insertSets(sets)
    }

    @Transaction
    suspend fun synchronizeWorkout(workoutEntry: WorkoutEntry) {
        val workoutToSync = workoutEntry.workout

        val existingWorkout = workoutToSync.serverId?.let { getWorkoutByServerId(it) }

        // Determine the latest favorite status and its timestamp
        val (isFavorite, isFavoriteChangedAt) = when {
            existingWorkout?.isFavoriteChangedAt == null -> {
                workoutToSync.isFavorite to workoutToSync.isFavoriteChangedAt
            }
            workoutToSync.isFavoriteChangedAt == null -> {
                existingWorkout.isFavorite to existingWorkout.isFavoriteChangedAt
            }
            workoutToSync.isFavoriteChangedAt > existingWorkout.isFavoriteChangedAt -> {
                workoutToSync.isFavorite to workoutToSync.isFavoriteChangedAt
            }
            else -> {
                existingWorkout.isFavorite to existingWorkout.isFavoriteChangedAt
            }
        }

        // Determine if the workout needs to be replaced
        val needsReplacement = when {
            existingWorkout == null -> true
            workoutToSync.updatedAt == null -> false
            existingWorkout.updatedAt == null -> true
            workoutToSync.updatedAt > existingWorkout.updatedAt -> true
            workoutToSync.updatedAt < existingWorkout.updatedAt -> false
            else -> true
        }

        if (needsReplacement) {
            existingWorkout?.let { deleteWorkout(workoutUUID = it.clientUUID) }
            insertWorkoutWithDetails(
                workout = workoutToSync.copy(
                    isFavorite = isFavorite,
                    isFavoriteChangedAt = isFavoriteChangedAt
                ),
                workoutEquipments = workoutEntry.workoutEquipments,
                workoutsMuscles = workoutEntry.workoutMuscles,
                workoutsTypes = workoutEntry.workoutTypes,
                workoutsExercises = workoutEntry.workoutExerciseEntries.map { it.workoutExercise },
                supersets = workoutEntry.workoutExerciseEntries.mapNotNull { it.superset },
                sets = workoutEntry.workoutExerciseEntries.flatMap { it.sets }
            )
        } else if (
            existingWorkout != null &&
            existingWorkout.isFavorite != isFavorite &&
            existingWorkout.isFavoriteChangedAt != isFavoriteChangedAt
        ) {
            updateWorkout(
                workout = existingWorkout.copy(
                    isFavorite = isFavorite,
                    isFavoriteChangedAt = isFavoriteChangedAt
                )
            )
        }
    }

}