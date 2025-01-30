package com.itami.workout_flow.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.workout_flow.core.domain.repository.AppSettings
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import com.itami.workout_flow.core.utils.DateTimeUtil
import com.itami.workout_flow.home.presentation.home.components.RoutineDayUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.isoDayNumber

class HomeViewModel(
    private val workoutRepository: WorkoutRepository,
    private val appSettings: AppSettings,
) : ViewModel() {

    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val currentWeek = DateTimeUtil.getCurrentWeekDates()

    init {
        observeScheduledWorkouts()
        observeShowSignIn()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.EditRoutineClick -> {
                sendUiEvent(HomeEvent.NavigateToRoutine)
            }

            is HomeAction.HideSignInCardClick -> {
                viewModelScope.launch {
                    appSettings.setShowSignInOnHomeScreen(showSignIn = false)
                }
            }

            is HomeAction.ProfilePictureClick -> {
                sendUiEvent(HomeEvent.NavigateToProfile)
            }

            is HomeAction.SearchBarClick -> {
                sendUiEvent(HomeEvent.NavigateToSearchWorkout)
            }

            is HomeAction.RoutineDayClick -> {
                _state.update { it.copy(selectedDate = action.routineDay.date) }
            }

            is HomeAction.SignInCardClick -> {
                sendUiEvent(HomeEvent.NavigateToSignIn)
            }

            is HomeAction.ScheduledWorkoutClick -> {
                sendUiEvent(
                    HomeEvent.NavigateToWorkoutDetails(action.scheduledWorkout.workoutPreview.id)
                )
            }

            is HomeAction.WorkoutTypeClick -> {
                sendUiEvent(HomeEvent.NavigateToSearchWorkoutByType(action.workoutType))
            }
        }
    }

    private fun sendUiEvent(event: HomeEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

    private fun observeShowSignIn() {
        viewModelScope.launch {
            appSettings.showSignInOnHomeScreen.collectLatest { showSignIn ->
                _state.update { it.copy(showSignInCard = showSignIn) }
            }
        }
    }

    private fun observeScheduledWorkouts() {
        viewModelScope.launch {
            workoutRepository.getScheduledWorkouts()
                .collectLatest { scheduledWorkouts ->
                    val routineDays = currentWeek.map { date ->
                        RoutineDayUI(
                            date = date,
                            scheduledWorkouts = scheduledWorkouts.filter {
                                it.dayOfWeek.dayNumber == date.dayOfWeek.isoDayNumber
                            }
                        )
                    }
                    _state.update { it.copy(routineDays = routineDays) }
                }
        }
    }

}
