package com.itami.workout_flow.core.domain.model.error

sealed class DataError : AppError() {

    sealed class Remote : DataError() {
        data object Unauthorized : Remote()
        data object NotFound : Remote()
        data object RequestTimeout : Remote()
        data object TooManyRequests : Remote()
        data object NoInternet : Remote()
        data object ServerError : Remote()
        data object Serialization : Remote()
        data object Unknown : Remote()
    }

    sealed class Local : DataError() {
        data object DiskFull : Local()
        data object Unknown : Local()
    }

}