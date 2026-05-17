package com.teladan.amaliah.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity

@Dao
interface MatrixDao {

    @Query("SELECT * FROM kriteria_matrix WHERE id = 1 LIMIT 1")
    suspend fun getMatrix(): KriteriaMatrixEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMatrix(matrix: KriteriaMatrixEntity)
}