package com.itami.workout_flow.routes

import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.utils.RESTAPI_VERSION
import io.ktor.resources.Resource

@Resource("$RESTAPI_VERSION/exercises")
class ExercisesRoute(
    val page: Int? = 1,
    val pageSize: Int? = 10,
    val query: String? = "",
    val muscles: List<Muscle>? = null,
    val exerciseTypes: List<ExerciseType>? = null,
    val equipments: List<Equipment>? = null
)