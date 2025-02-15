package com.itami.workout_flow.core.data.local.preferences.user

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

object DataStoreCurrentUserSerializer : OkioSerializer<DataStoreCurrentUser> {

    override val defaultValue: DataStoreCurrentUser
        get() = DataStoreCurrentUser.Guest

    override suspend fun readFrom(source: BufferedSource): DataStoreCurrentUser {
        return try {
            Json.decodeFromString(
                deserializer = DataStoreCurrentUser.serializer(),
                string = source.readByteArray().decodeToString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: DataStoreCurrentUser, sink: BufferedSink) {
        val userByteArray = Json.encodeToString(
            serializer = DataStoreCurrentUser.serializer(),
            value = t
        ).encodeToByteArray()
        sink.write(userByteArray)
    }
}