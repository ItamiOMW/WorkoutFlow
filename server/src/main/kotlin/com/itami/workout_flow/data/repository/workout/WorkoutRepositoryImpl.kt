package com.itami.workout_flow.data.repository.workout

import com.itami.workout_flow.data.database.DatabaseFactory.dbQuery
import com.itami.workout_flow.data.database.entity.WorkoutEntity
import com.itami.workout_flow.data.database.table.Users
import com.itami.workout_flow.data.database.table.WorkoutFavorites
import com.itami.workout_flow.data.database.table.WorkoutMuscles
import com.itami.workout_flow.data.database.table.WorkoutTypes
import com.itami.workout_flow.data.database.table.Workouts
import com.itami.workout_flow.data.mapper.toWorkout
import com.itami.workout_flow.data.model.workout.Workout
import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.WorkoutTimeFilter
import com.itami.workout_flow.model.WorkoutType
import com.itami.workout_flow.model.WorkoutsSort
import com.itami.workout_flow_ktor.data.database.table.WorkoutEquipments
import org.jetbrains.exposed.sql.Case
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Sum
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.intLiteral
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.trim
import java.time.OffsetDateTime

class WorkoutRepositoryImpl : WorkoutRepository {

    override suspend fun getWorkouts(
        userId: Long?,
        query: String,
        page: Int,
        pageSize: Int,
        createdBeforeCursor: OffsetDateTime,
        workoutsSort: WorkoutsSort,
        workoutTypes: List<WorkoutType>,
        equipments: List<Equipment>,
        muscles: List<Muscle>,
        timeFilters: List<WorkoutTimeFilter>
    ): List<Workout> {
        return dbQuery {
            val workoutIds = Workouts
                .join(Users, JoinType.INNER, Workouts.authorId, Users.id)
                .join(WorkoutEquipments, JoinType.LEFT, Workouts.id, WorkoutEquipments.workoutId)
                .join(WorkoutTypes, JoinType.LEFT, Workouts.id, WorkoutTypes.workoutId)
                .join(WorkoutMuscles, JoinType.LEFT, Workouts.id, WorkoutMuscles.workoutId)
                .join(WorkoutFavorites, JoinType.LEFT, Workouts.id, WorkoutFavorites.workoutId)
                .select(
                    Workouts.id,
                    Workouts.createdAt,
                    Users.id,
                    WorkoutEquipments.equipment,
                    WorkoutTypes.workoutType,
                    WorkoutMuscles.muscle,
                )
                .groupBy(
                    Workouts.id,
                    Users.id,
                    WorkoutEquipments.equipment,
                    WorkoutTypes.workoutType,
                    WorkoutMuscles.muscle,
                )
                .apply {
                    andWhere { Workouts.createdAt.lessEq(createdBeforeCursor) }

                    andWhere { (Workouts.isPublic eq true) }

                    andWhere {
                        (Workouts.name.trim().lowerCase() like "%$query%") or
                                (Users.username.trim().lowerCase() like "%$query%")
                    }

                    if (workoutTypes.isNotEmpty()) {
                        andWhere { WorkoutTypes.workoutType inList workoutTypes }
                    }

                    if (muscles.isNotEmpty()) {
                        andWhere { (WorkoutMuscles.muscle inList muscles) }
                    }

                    if (equipments.isNotEmpty()) {
                        andWhere { (WorkoutEquipments.equipment inList equipments) }
                    }

                    if (timeFilters.isNotEmpty()) {
                        andWhere {
                            timeFilters.map { range ->
                                when (range) {
                                    WorkoutTimeFilter.LessThan30Min -> (Workouts.durationMin greaterEq 0) and (Workouts.durationMin lessEq 30)
                                    WorkoutTimeFilter.Between30And60Min -> (Workouts.durationMin greaterEq 30) and (Workouts.durationMin lessEq 60)
                                    WorkoutTimeFilter.Between60And90Min -> (Workouts.durationMin greaterEq 60) and (Workouts.durationMin lessEq 90)
                                    WorkoutTimeFilter.MoreThan90Min -> Workouts.durationMin greaterEq 60
                                }
                            }.reduce { acc, condition -> acc or condition }
                        }
                    }
                }
                .orderBy(
                    when (workoutsSort) {
                        WorkoutsSort.Newest -> Workouts.createdAt to SortOrder.DESC
                        WorkoutsSort.Oldest -> Workouts.createdAt to SortOrder.ASC
                        WorkoutsSort.MostLiked -> {
                            Sum(
                                expr = Case()
                                    .When(WorkoutFavorites.isFavorite eq true, intLiteral(1))
                                    .Else(intLiteral(0)),
                                columnType = IntegerColumnType()
                            ) to SortOrder.DESC
                        }

                        WorkoutsSort.LeastLiked -> {
                            Sum(
                                expr = Case()
                                    .When(WorkoutFavorites.isFavorite eq true, intLiteral(1))
                                    .Else(intLiteral(0)),
                                columnType = IntegerColumnType()
                            ) to SortOrder.ASC
                        }
                    }
                )
                .limit(n = pageSize, offset = ((page - 1) * pageSize).toLong()).map {
                    it[Workouts.id]
                }

            return@dbQuery WorkoutEntity
                .forEntityIds(workoutIds)
                .sortedWith(
                    when (workoutsSort) {
                        WorkoutsSort.Newest -> compareByDescending { it.createdAt }
                        WorkoutsSort.Oldest -> compareBy { it.createdAt }
                        WorkoutsSort.MostLiked -> compareByDescending { it.favorites.count { it.isFavorite } }
                        WorkoutsSort.LeastLiked -> compareBy { it.favorites.count { it.isFavorite } }
                    }
                )
                .map { it.toWorkout(userId) }
        }
    }

    override suspend fun getPopularWorkouts(limit: Int, userId: Long?): List<Workout> {
        return dbQuery {
            WorkoutEntity
                .all()
                .filter { it.isPublic }
                .sortedByDescending { it.favorites.count { favoriteEntity -> favoriteEntity.isFavorite } }
                .take(limit)
                .map { it.toWorkout(userId) }
        }
    }

}