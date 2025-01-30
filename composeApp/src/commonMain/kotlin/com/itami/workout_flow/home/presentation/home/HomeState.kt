package com.itami.workout_flow.home.presentation.home

import com.itami.workout_flow.core.domain.model.user.CurrentUser
import com.itami.workout_flow.core.utils.DateTimeUtil
import com.itami.workout_flow.home.presentation.home.components.RoutineDayUI
import com.itami.workout_flow.model.WorkoutType
import kotlinx.datetime.LocalDate

data class HomeState(
    val currentUser: CurrentUser = CurrentUser.Guest,
    val currentDate: LocalDate = DateTimeUtil.getCurrentDate(),
    val selectedDate: LocalDate = currentDate,
    val routineDays: List<RoutineDayUI> = emptyList(),
    val workoutTypes: List<WorkoutType> = WorkoutType.entries,
    val showSignInCard: Boolean = false,
) {
    val selectedRoutineDay: RoutineDayUI
        get() = routineDays.firstOrNull { it.date == selectedDate }
            ?: RoutineDayUI(currentDate, emptyList())
}
