package com.itami.workout_flow.app.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.ratelimit.*
import kotlin.time.Duration.Companion.seconds

val DEFAULT_RATE_LIMIT = RateLimitName("app-rate-limit")
val AUTH_RATE_LIMIT = RateLimitName("auth-rate-limit")

fun Application.configureRateLimit() {
    install(RateLimit) {
        register(DEFAULT_RATE_LIMIT) {
            rateLimiter(limit = 60, refillPeriod = 60.seconds)
            requestKey { it.request.origin.remoteAddress }
        }
        register(AUTH_RATE_LIMIT) {
            rateLimiter(limit = 10, refillPeriod = 60.seconds)
            requestKey { it.request.origin.remoteAddress }
        }
    }
}