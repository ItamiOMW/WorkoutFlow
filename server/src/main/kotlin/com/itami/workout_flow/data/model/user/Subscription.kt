package com.itami.workout_flow.data.model.user

import java.time.OffsetDateTime

sealed class Subscription {

    data object Basic : Subscription()

    data class Premium(val expirationDateTime: OffsetDateTime): Subscription()

}