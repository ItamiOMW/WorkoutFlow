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
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val corePlatformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }

    single<WorkoutFlowDatabase> {
        val documentDirectory = requireNotNull(
            NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
        )
        val dbFile = documentDirectory.path + "/${WorkoutFlowDatabase.DB_NAME}"
        Room.databaseBuilder<WorkoutFlowDatabase>(name = dbFile)
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single<DataStore<DataStoreCurrentUser>>(qualifier = dataStoreCurrentUserQualifier) {
        DataStoreFactory.create(
            storage = OkioStorage(
                fileSystem = FileSystem.SYSTEM,
                serializer = DataStoreCurrentUserSerializer,
                producePath = {
                    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                        directory = NSDocumentDirectory,
                        inDomain = NSUserDomainMask,
                        appropriateForURL = null,
                        create = false,
                        error = null,
                    )
                    val stringPath =
                        requireNotNull(documentDirectory).path + "/$currentUserDataStoreFileName"
                    stringPath.toPath()
                }
            )
        )
    }

    single<DataStore<Preferences>>(qualifier = dataStorePreferencesQualifier) {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                val stringPath =
                    requireNotNull(documentDirectory).path + "/$preferencesDataStoreFileName"
                stringPath.toPath()
            }
        )
    }
}