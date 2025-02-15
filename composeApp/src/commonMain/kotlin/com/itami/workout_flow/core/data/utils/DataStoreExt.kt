package com.itami.workout_flow.core.data.utils

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

internal const val preferencesDataStoreFileName = "WorkoutFlow.preferences_pb"
internal const val currentUserDataStoreFileName = "WorkoutFlow.currentuser_pb"

fun Flow<Preferences>.handleDataStoreException(): Flow<Preferences> {
    return this.catch { exception ->
        if (exception is IOException || exception is CorruptionException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
}