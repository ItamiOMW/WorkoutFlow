package com.itami.workout_flow.core.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itami.workout_flow.core.data.local.database.converter.EnumConverter
import com.itami.workout_flow.core.data.local.database.converter.InstantConverter
import com.itami.workout_flow.core.data.local.database.dao.ExerciseDao
import com.itami.workout_flow.core.data.local.database.dao.UserProfileDao
import com.itami.workout_flow.core.data.local.database.dao.WorkoutDao
import com.itami.workout_flow.core.data.local.database.entity.workout.*
import com.itami.workout_flow.core.data.local.database.entity.user.*
import com.itami.workout_flow.core.data.local.database.entity.exercise.*

@Database(
    entities = [
        UserProfileEntity::class,
        WorkoutEntity::class,
        ScheduledWorkoutEntity::class,
        WorkoutTypeEntity::class,
        WorkoutMuscleEntity::class,
        WorkoutEquipmentEntity::class,
        WorkoutExerciseEntity::class,
        SetEntity::class,
        SupersetEntity::class,
        ExerciseEntity::class,
        ExerciseStepEntity::class,
        ExerciseEquipmentEntity::class,
        ExerciseMuscleInvolvementEntity::class,
    ],
    views = [
        WorkoutPreviewDbView::class
    ],
    version = 1,
)
@TypeConverters(
    EnumConverter::class,
    InstantConverter::class
)
@ConstructedBy(WorkoutFlowDbConstructor::class)
abstract class WorkoutFlowDatabase : RoomDatabase() {

    abstract val workoutDao: WorkoutDao

    abstract val exerciseDao: ExerciseDao

    abstract val userProfileDao: UserProfileDao

    companion object {
        const val DB_NAME = "workout_flow.db"
    }

}