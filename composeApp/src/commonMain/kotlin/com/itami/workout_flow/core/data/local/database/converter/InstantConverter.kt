package com.itami.workout_flow.core.data.local.database.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class InstantConverter {

    @TypeConverter
    fun toInstant(value: String): Instant {
        return Instant.parse(value)
    }

    @TypeConverter
    fun fromInstant(value: Instant): String {
        return value.toString()
    }

}