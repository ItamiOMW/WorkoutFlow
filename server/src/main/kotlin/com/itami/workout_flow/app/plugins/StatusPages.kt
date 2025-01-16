package com.itami.workout_flow.app.plugins

import com.itami.workout_flow.data.dto.response.ErrorResponse
import com.itami.workout_flow.utils.AppError
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<AppError> { call, cause ->
            cause.printStackTrace()
            call.respond(
                status = cause.httpStatusCode,
                message = ErrorResponse(cause.message)
            )
        }
        exception<Throwable> { call, cause ->
            cause.printStackTrace()
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponse(message = "Internal server error occurred. Please, try again later.")
            )
        }
    }
}