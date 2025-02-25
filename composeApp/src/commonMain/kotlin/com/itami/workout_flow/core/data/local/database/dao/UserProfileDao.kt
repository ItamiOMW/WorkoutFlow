package com.itami.workout_flow.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itami.workout_flow.core.data.local.database.entity.user.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM user_profiles WHERE id = :userId LIMIT 1")
    fun getUserProfileByIdFlow(userId: Long): Flow<UserProfileEntity>

    @Query("SELECT * FROM user_profiles WHERE id = :userId LIMIT 1")
    suspend fun getUserProfileById(userId: Long): UserProfileEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserProfileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserProfileEntity>)

    @Query("DELETE FROM user_profiles WHERE id = :userId")
    suspend fun delete(userId: Long)

    @Query("DELETE FROM user_profiles")
    suspend fun deleteAll()

}