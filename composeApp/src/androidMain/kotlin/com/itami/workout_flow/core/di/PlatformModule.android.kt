package com.itami.workout_flow.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.itami.workout_flow.core.data.local.database.WorkoutFlowDatabase
import com.itami.workout_flow.core.data.local.preferences.user.DataStoreCurrentUser
import com.itami.workout_flow.core.data.local.preferences.user.DataStoreCurrentUserSerializer
import com.itami.workout_flow.core.data.utils.currentUserDataStoreFileName
import com.itami.workout_flow.core.data.utils.preferencesDataStoreFileName
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val corePlatformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }

    single<WorkoutFlowDatabase> {
        val context = androidContext()
        val dbFile = context.getDatabasePath(WorkoutFlowDatabase.DB_NAME)
        Room.databaseBuilder<WorkoutFlowDatabase>(context = context, name = dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single<DataStore<DataStoreCurrentUser>>(qualifier = dataStoreCurrentUserQualifier) {
        DataStoreFactory.create(
            storage = OkioStorage(
                fileSystem = FileSystem.SYSTEM,
                serializer = DataStoreCurrentUserSerializer,
                producePath = {
                    androidContext().filesDir.resolve(currentUserDataStoreFileName).absolutePath.toPath()
                }
            )
        )
    }

    single<DataStore<Preferences>>(qualifier = dataStorePreferencesQualifier) {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                androidContext().filesDir.resolve(preferencesDataStoreFileName).absolutePath.toPath()
            }
        )
    }
}