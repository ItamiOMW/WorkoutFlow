package com.itami.workout_flow.utils

import io.ktor.http.HttpStatusCode

sealed class AppError(
    override val message: String,
    val httpStatusCode: HttpStatusCode,
): Exception() {

    data class UnauthorizedError(override val message: String = "Unauthorized.") : AppError(
        message = message,
        httpStatusCode = HttpStatusCode.Unauthorized
    )

    data class ForbiddenError(override val message: String = "Forbidden.") : AppError(
        message = message,
        httpStatusCode = HttpStatusCode.Forbidden
    )

    data class BadRequestError(override val message: String = "Bad Request.") : AppError(
        message = message,
        httpStatusCode = HttpStatusCode.BadRequest
    )

    data class NotFoundError(override val message: String = "Not found.") : AppError(
        message = message,
        httpStatusCode = HttpStatusCode.NotFound
    )

    data class ConflictError(override val message: String = "Conflict.") : AppError(
        message = message,
        httpStatusCode = HttpStatusCode.Conflict
    )

}