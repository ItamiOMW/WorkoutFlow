package com.itami.workout_flow.data.model.user

import java.time.LocalDateTime

sealed class Subscription {

    data object Basic : Subscription()

    data class Premium(val expirationDateTime: LocalDateTime): Subscription()

}