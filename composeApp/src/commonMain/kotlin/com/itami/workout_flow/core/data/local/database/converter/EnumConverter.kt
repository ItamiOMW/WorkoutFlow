package com.itami.workout_flow.core.data.local.database.converter

import androidx.room.TypeConverter
import com.itami.workout_flow.model.DayOfWeek
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.MuscleRole
import com.itami.workout_flow.model.UserProfileSubscription
import com.itami.workout_flow.model.WorkoutType

class EnumConverter {

    // Equipment
    @TypeConverter
    fun fromEquipment(value: Equipment): String {
        return value.name
    }

    @TypeConverter
    fun toEquipment(value: String): Equipment {
        return Equipment.valueOf(value)
    }


    // Muscle
    @TypeConverter
    fun fromMuscle(value: Muscle): String {
        return value.name
    }

    @TypeConverter
    fun toMuscle(value: String): Muscle {
        return Muscle.valueOf(value)
    }


    // Muscle Role
    @TypeConverter
    fun fromMuscleRole(value: MuscleRole): String {
        return value.name
    }

    @TypeConverter
    fun toMuscleRole(value: String): MuscleRole {
        return MuscleRole.valueOf(value)
    }


    // Exercise Type
    @TypeConverter
    fun fromExerciseType(value: ExerciseType): String {
        return value.name
    }

    @TypeConverter
    fun toExerciseType(value: String): ExerciseType {
        return ExerciseType.valueOf(value)
    }


    // Workout Type
    @TypeConverter
    fun fromWorkoutType(value: WorkoutType): String {
        return value.name
    }

    @TypeConverter
    fun toWorkoutType(value: String): WorkoutType {
        return WorkoutType.valueOf(value)
    }


    // Day Of Week
    @TypeConverter
    fun fromDayOfWeek(value: DayOfWeek): String {
        return value.name
    }

    @TypeConverter
    fun toDayOfWeek(value: String): DayOfWeek {
        return DayOfWeek.valueOf(value)
    }


    // User Profile Subscription
    @TypeConverter
    fun fromUserProfileSubscription(value: UserProfileSubscription): String {
        return value.name
    }

    @TypeConverter
    fun toUserProfileSubscription(value: String): UserProfileSubscription {
        return UserProfileSubscription.valueOf(value)
    }

}