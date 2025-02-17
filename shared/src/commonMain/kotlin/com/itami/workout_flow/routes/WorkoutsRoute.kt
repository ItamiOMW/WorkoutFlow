package com.itami.workout_flow.routes

import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutTimeFilter
import com.itami.workout_flow.model.WorkoutType
import com.itami.workout_flow.model.WorkoutsSort
import com.itami.workout_flow.utils.RESTAPI_VERSION
import io.ktor.resources.Resource

@Resource("$RESTAPI_VERSION/workouts")
class WorkoutsRoute(
    val lastItemId: Long? = null,
    val pageSize: Int? = 10,
    val query: String? = "",
    val sort: WorkoutsSort? = WorkoutsSort.Newest,
    val types: List<WorkoutType>? = null,
    val equipment: List<Equipment>? = null,
    val muscles: List<Muscle>? = null,
    val timeFilters: List<WorkoutTimeFilter>? = null,
) {

}