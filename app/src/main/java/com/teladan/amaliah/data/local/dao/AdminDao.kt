package com.teladan.amaliah.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teladan.amaliah.data.local.entity.Admin

@Dao
interface AdminDao {
    @Query("SELECT * FROM admin_table WHERE username = :user AND password = :pass LIMIT 1")
    suspend fun loginAdmin(user: String, pass: String): Admin?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdmin(admin: Admin)
}
