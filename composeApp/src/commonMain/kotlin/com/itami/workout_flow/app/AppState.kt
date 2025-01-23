package com.itami.workout_flow.app

import com.itami.workout_flow.core.domain.model.settings.Theme

sealed interface AppState {

    data object Initializing : AppState

    data class State(
        val showOnboarding: Boolean = false,
        val theme: Theme = Theme.SYSTEM_THEME,
    ) : AppState

}
