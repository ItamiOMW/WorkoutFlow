package com.itami.workout_flow.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.workout_flow.core.domain.repository.AppSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    appSettings: AppSettings
): ViewModel() {

    val state = combine(
        appSettings.showOnboarding,
        appSettings.theme,
    ) { showOnboarding, theme ->
        AppState.State(
            showOnboarding = showOnboarding,
            theme = theme
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AppState.Initializing
    )

}
