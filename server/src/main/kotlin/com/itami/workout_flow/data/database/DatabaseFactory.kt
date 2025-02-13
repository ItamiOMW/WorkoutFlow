package com.itami.workout_flow.data.database

import com.itami.workout_flow.data.database.table.Users
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
            )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) { block() }
    }

    private fun getHikariDataSource(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = System.getenv("DRIVER_CLASS_NAME")
            jdbcUrl = System.getenv("JDBC_URL")
            username = System.getenv("DB_USERNAME")
            password = System.getenv("DB_PASSWORD")
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        return HikariDataSource(config)
    }

}