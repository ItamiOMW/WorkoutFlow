package com.itami.workout_flow.data.database

import com.itami.workout_flow.data.database.table.ExerciseEquipments
import com.itami.workout_flow.data.database.table.ExerciseFavorites
import com.itami.workout_flow.data.database.table.ExerciseMuscleInvolvements
import com.itami.workout_flow.data.database.table.ExerciseSteps
import com.itami.workout_flow.data.database.table.Exercises
import com.itami.workout_flow.data.database.table.Sets
import com.itami.workout_flow.data.database.table.Supersets
import com.itami.workout_flow.data.database.table.Users
import com.itami.workout_flow.data.database.table.WorkoutExercises
import com.itami.workout_flow.data.database.table.WorkoutFavorites
import com.itami.workout_flow.data.database.table.WorkoutMuscles
import com.itami.workout_flow.data.database.table.WorkoutTypes
import com.itami.workout_flow.data.database.table.Workouts
import com.itami.workout_flow_ktor.data.database.table.WorkoutEquipments
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val dataSource = getHikariDataSource()
        val database = Database.connect(datasource = dataSource)

        transaction(db = database) {
            SchemaUtils.create(
                Users,
                Exercises,
                ExerciseSteps,
                ExerciseEquipments,
                ExerciseMuscleInvolvements,
                ExerciseFavorites,
                Workouts,
                WorkoutEquipments,
                WorkoutTypes,
                WorkoutMuscles,
                WorkoutExercises,
                WorkoutFavorites,
                Sets,
                Supersets,
            )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) { block() }
    }

    private fun getHikariDataSource(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = System.getenv("DB_DRIVER_NAME")
            jdbcUrl = System.getenv("JDBC_URL")
            username = System.getenv("DB_USER")
            password = System.getenv("DB_PASSWORD")
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        return HikariDataSource(config)
    }

}