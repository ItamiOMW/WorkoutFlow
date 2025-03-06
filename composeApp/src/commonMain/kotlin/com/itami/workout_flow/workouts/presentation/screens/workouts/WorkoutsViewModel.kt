package com.itami.workout_flow.workouts.presentation.screens.workouts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.itami.workout_flow.core.domain.repository.WorkoutRepository
import com.itami.workout_flow.core.presentation.navigation.AppGraph.Workouts.WorkoutsScreen.WorkoutsLaunchMode
import com.itami.workout_flow.model.WorkoutType
import com.itami.workout_flow.model.WorkoutsFilter
import com.itami.workout_flow.model.WorkoutsSort
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class WorkoutsViewModel(
    private val workoutRepository: WorkoutRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _events = Channel<WorkoutsEvent>()
    val events = _events.receiveAsFlow()

    private val _searchQuery = MutableStateFlow("")
    private val _showSearchQuery = MutableStateFlow(false)
    private val _workoutsFilter = MutableStateFlow(WorkoutsFilter())
    private val _workoutSort = MutableStateFlow(WorkoutsSort.Newest)
    private val _selectedPagerScreen = MutableStateFlow(WorkoutsState.WorkoutsPagerScreen.CUSTOM)
    private val _bottomSheetContent = MutableStateFlow<WorkoutsState.WorkoutsBottomSheetContent?>(null)

    val customWorkouts = combine(
        _searchQuery.debounce(500L),
        _workoutsFilter,
        _workoutSort
    ) { searchQuery, filter, sort ->
        Triple(searchQuery, filter, sort)
    }.flatMapLatest { (searchQuery, filter, sort) ->
        workoutRepository.observeCustomWorkoutPreviews(searchQuery, sort, filter)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val favoriteWorkouts = combine(
        _searchQuery.debounce(500L),
        _workoutsFilter,
        _workoutSort
    ) { searchQuery, filter, sort ->
        Triple(searchQuery, filter, sort)
    }.flatMapLatest { (searchQuery, filter, sort) ->
        workoutRepository.observeFavoriteWorkoutPreviews(searchQuery, sort, filter)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val searchWorkouts = combine(
        _searchQuery.debounce(500L),
        _workoutsFilter,
        _workoutSort
    ) { searchQuery, filter, sort ->
        Triple(searchQuery, filter, sort)
    }.flatMapLatest { (searchQuery, filter, sort) ->
        workoutRepository.observePagingWorkoutPreviews(searchQuery, sort, filter)
    }.cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PagingData.empty()
        )

    val state = com.itami.workout_flow.core.utils.combine(
        _searchQuery,
        _showSearchQuery,
        _workoutsFilter,
        _workoutSort,
        _bottomSheetContent,
        _selectedPagerScreen
    ) { searchQuery, showSearchQuery, filter, sort, bottomSheetContent, pagerScreen ->
        WorkoutsState(
            searchQuery = searchQuery,
            showSearchQuery = showSearchQuery,
            workoutSort = sort,
            workoutsFilter = filter,
            workoutsBottomSheetContent = bottomSheetContent,
            selectedPagerScreen = pagerScreen
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = WorkoutsState()
    )

    init {
        savedStateHandle.get<String>("launchMode")?.let { launchModeStr ->
            val launchMode = WorkoutsLaunchMode.valueOf(launchModeStr)
            val selectedPagerScreen = when (launchMode) {
                WorkoutsLaunchMode.Default -> WorkoutsState.WorkoutsPagerScreen.CUSTOM
                WorkoutsLaunchMode.Favorites -> WorkoutsState.WorkoutsPagerScreen.FAVORITES
                WorkoutsLaunchMode.Search -> WorkoutsState.WorkoutsPagerScreen.SEARCH
            }
            _selectedPagerScreen.update { selectedPagerScreen }
        }

        savedStateHandle.get<String>("workoutType")?.let { workoutTypeStr ->
            val workoutType = WorkoutType.valueOf(workoutTypeStr)
            _workoutsFilter.update { it.copy(selectedWorkoutTypes = listOf(workoutType)) }
        }

        savedStateHandle.get<String>("workoutSort")?.let { workoutSortStr ->
            val workoutSort = WorkoutsSort.valueOf(workoutSortStr)
            _workoutSort.update { workoutSort }
        }
    }

    fun onAction(action: WorkoutsAction) {
        when (action) {
            WorkoutsAction.CloseSearchClick -> {
                _showSearchQuery.update { false }
            }

            WorkoutsAction.OpenSearchClick -> {
                _showSearchQuery.update { true }
            }

            WorkoutsAction.CreateWorkoutFABClick -> {
                sendEvent(WorkoutsEvent.NavigateToCreateWorkout)
            }

            WorkoutsAction.FilterClick -> {
                _bottomSheetContent.update { WorkoutsState.WorkoutsBottomSheetContent.FILTER }
            }

            WorkoutsAction.SortClick -> {
                _bottomSheetContent.update { WorkoutsState.WorkoutsBottomSheetContent.SORT }
            }

            WorkoutsAction.HideBottomSheet -> {
                _bottomSheetContent.update { null }
            }

            is WorkoutsAction.ChangeWorkoutsFilter -> {
                _workoutsFilter.update { action.workoutsFilter }
            }

            is WorkoutsAction.ChangeWorkoutsSort -> {
                _workoutSort.update { action.workoutSort }
            }

            is WorkoutsAction.SearchQueryChange -> {
                _searchQuery.update { action.newValue }
            }

            is WorkoutsAction.WorkoutClick -> {
                sendEvent(WorkoutsEvent.NavigateToWorkoutDetails(action.workoutId))
            }

            is WorkoutsAction.SelectPagerScreen -> {
                _selectedPagerScreen.update { action.pagerScreen }
            }
        }
    }

    private fun sendEvent(event: WorkoutsEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }


}
