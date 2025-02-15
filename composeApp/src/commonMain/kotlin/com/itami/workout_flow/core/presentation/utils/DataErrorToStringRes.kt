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
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.SERVER -> Res.string.error_unknown
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.UNAUTHORIZED -> Res.string.error_unauthorized
        DataError.Remote.NOT_FOUND -> Res.string.error_not_found
    }
}