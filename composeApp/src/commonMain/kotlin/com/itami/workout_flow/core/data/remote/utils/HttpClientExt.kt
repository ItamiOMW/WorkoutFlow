package com.itami.workout_flow.core.data.remote.utils

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext
import com.itami.workout_flow.core.domain.model.error.DataError
import com.itami.workout_flow.core.domain.model.result.AppResult

suspend inline fun <reified T> safeRequest(
    execute: () -> HttpResponse
): AppResult<T, DataError.Remote> {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        e.printStackTrace()
        return AppResult.Error(DataError.Remote.RequestTimeout)
    } catch(e: UnresolvedAddressException) {
        e.printStackTrace()
        return AppResult.Error(DataError.Remote.NoInternet)
    } catch (e: Exception) {
        e.printStackTrace()
        coroutineContext.ensureActive()
        return AppResult.Error(DataError.Remote.Unknown)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): AppResult<T, DataError.Remote> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                AppResult.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                AppResult.Error(DataError.Remote.Serialization)
            }
        }
        401 -> AppResult.Error(DataError.Remote.Unauthorized)
        404 -> AppResult.Error(DataError.Remote.NotFound)
        408 -> AppResult.Error(DataError.Remote.RequestTimeout)
        429 -> AppResult.Error(DataError.Remote.TooManyRequests)
        in 500..599 -> AppResult.Error(DataError.Remote.ServerError)
        else -> AppResult.Error(DataError.Remote.Unknown)
    }
}