package com.alonsorafael.quizapp_android_firebase.data.local.dao

import androidx.room.*
import com.alonsorafael.quizapp_android_firebase.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    
    @Query("SELECT * FROM users WHERE uid = :uid")
    fun getUserById(uid: String): Flow<UserEntity?>
    
    @Query("SELECT * FROM users ORDER BY totalScore DESC LIMIT 10")
    fun getTopUsers(): Flow<List<UserEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    @Delete
    suspend fun deleteUser(user: UserEntity)
    
    @Query("DELETE FROM users WHERE uid = :uid")
    suspend fun deleteUserById(uid: String)
}
