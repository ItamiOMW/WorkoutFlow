package com.itami.workout_flow.auth.data.mapper

import com.itami.workout_flow.core.domain.model.user.CurrentUser
import com.itami.workout_flow.dto.response.SubscriptionResponse
import com.itami.workout_flow.dto.response.UserResponse

fun UserResponse.toCurrentAuthenticatedUser() = CurrentUser.Authenticated(
    id = this.id,
    name = this.name,
    username = this.username,
    email = this.email,
    profilePictureUrl = this.profilePictureUrl,
    subscription = when(val subscription = this.subscription) {
        is SubscriptionResponse.Basic -> CurrentUser.Subscription.Basic
        is SubscriptionResponse.Premium -> CurrentUser.Subscription.Premium(subscription.expirationDateTime)
    }

)