package com.itami.workout_flow.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.workout_flow.core.domain.model.settings.Theme
import com.itami.workout_flow.core.domain.repository.AppSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    appSettings: AppSettings
) : ViewModel() {

    val theme = appSettings.theme
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Theme.SYSTEM_THEME
        )

}
