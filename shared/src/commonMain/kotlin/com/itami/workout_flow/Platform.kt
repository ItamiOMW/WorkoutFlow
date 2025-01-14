package com.itami.workout_flow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform