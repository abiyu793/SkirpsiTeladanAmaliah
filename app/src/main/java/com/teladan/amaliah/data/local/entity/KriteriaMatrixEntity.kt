package com.teladan.amaliah.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kriteria_matrix")
data class KriteriaMatrixEntity(
    @PrimaryKey
    val id: Int = 1, // Hanya butuh 1 baris di database

    // Matriks Perbandingan (Crisp 1-9)
    val praktik_vs_akademik: Double = 5.0,
    val disiplin_vs_akademik: Double = 3.0,
    val praktik_vs_hadir: Double = 7.0,
    val akademik_vs_hadir: Double = 1.0,
    val praktik_vs_disiplin: Double = 1.0,
    val hadir_vs_disiplin: Double = 1.0
)