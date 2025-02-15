package com.itami.workout_flow.core.data.local.preferences.user

import androidx.datastore.core.DataStore
import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult
import com.itami.workout_flow.core.domain.model.user.CurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException

class DataStoreCurrentUserPreferences(
    private val dataStore: DataStore<DataStoreCurrentUser>
) : CurrentUserPreferences {

    override val user: Flow<CurrentUser>
        get() = dataStore.data.map { dataStoreUser ->
            when (dataStoreUser) {
                is DataStoreCurrentUser.Authenticated -> {
                    CurrentUser.Authenticated(
                        id = dataStoreUser.id,
                        name = dataStoreUser.name,
                        username = dataStoreUser.username,
                        email = dataStoreUser.email,
                        profilePictureUrl = dataStoreUser.profilePictureUrl,
                        subscription = when (val subscription = dataStoreUser.subscription) {
                            is DataStoreSubscription.Basic -> CurrentUser.Subscription.Basic
                            is DataStoreSubscription.Premium -> CurrentUser.Subscription.Premium(
                                subscription.expirationDateTime
                            )
                        },
                    )
                }

                is DataStoreCurrentUser.Guest -> CurrentUser.Guest
            }
        }

    override suspend fun setUser(user: CurrentUser): AppResult<Unit, DataError.Local> {
        return try {
            dataStore.updateData { dataStoreUser ->
                when (user) {
                    is CurrentUser.Authenticated -> {
                        DataStoreCurrentUser.Authenticated(
                            id = user.id,
                            name = user.name,
                            username = user.username,
                            email = user.email,
                            profilePictureUrl = user.profilePictureUrl,
                            subscription = when (val subscription = user.subscription) {
                                is CurrentUser.Subscription.Basic -> DataStoreSubscription.Basic
                                is CurrentUser.Subscription.Premium -> DataStoreSubscription.Premium(
                                    subscription.expirationDateTime
                                )
                            },
                        )
                    }

                    is CurrentUser.Guest -> DataStoreCurrentUser.Guest
                }
            }
            AppResult.Success(Unit)
        } catch (exception: IOException) {
            exception.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        } catch (exception: Exception) {
            exception.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }
}