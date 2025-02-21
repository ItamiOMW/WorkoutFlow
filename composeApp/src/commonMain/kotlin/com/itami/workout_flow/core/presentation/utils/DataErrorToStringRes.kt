package com.itami.workout_flow.core.presentation.utils

import com.itami.workout_flow.core.domain.model.error.DataError
import org.jetbrains.compose.resources.StringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.error_disk_full
import workoutflow.composeapp.generated.resources.error_no_internet
import workoutflow.composeapp.generated.resources.error_not_found
import workoutflow.composeapp.generated.resources.error_request_timeout
import workoutflow.composeapp.generated.resources.error_serialization
import workoutflow.composeapp.generated.resources.error_too_many_requests
import workoutflow.composeapp.generated.resources.error_unauthorized
import workoutflow.composeapp.generated.resources.error_unknown

fun DataError.toStringRes(): StringResource {
    return when(this) {
        DataError.Local.DiskFull -> Res.string.error_disk_full
        DataError.Local.Unknown -> Res.string.error_unknown
        DataError.Remote.RequestTimeout -> Res.string.error_request_timeout
        DataError.Remote.TooManyRequests -> Res.string.error_too_many_requests
        DataError.Remote.NoInternet -> Res.string.error_no_internet
        DataError.Remote.ServerError -> Res.string.error_unknown
        DataError.Remote.Serialization -> Res.string.error_serialization
        DataError.Remote.Unknown -> Res.string.error_unknown
        DataError.Remote.Unauthorized -> Res.string.error_unauthorized
        DataError.Remote.NotFound -> Res.string.error_not_found
    }
}