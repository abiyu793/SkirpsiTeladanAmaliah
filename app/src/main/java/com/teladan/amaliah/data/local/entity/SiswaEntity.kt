package com.teladan.amaliah.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "siswa_table")
data class SiswaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nis: String,
    val nama: String,
    val jurusan: String,
    val tingkat_kelas: String,
    val tahun_ajaran: String,

    // Sub-Kriteria Akademik
    val nilai_rapor: Double,
    val nilai_teori: Double,

    // Sub-Kriteria Praktik
    val nilai_lab: Double,
    val nilai_pkl: Double,

    // Sub-Kriteria Kehadiran
    val persentase_hadir: Double,
    val jam_terlambat: Double,

    // Sub-Kriteria Kedisiplinan
    val poin_pelanggaran: Double,
    val skor_sikap: Double
) {
    // LOGIKA PRA-PEMROSESAN (Otomatis dihitung saat diakses oleh RankingFragment)
    val rataAkademik: Double get() = (nilai_rapor + nilai_teori) / 2.0
    val rataPraktik: Double get() = if (nilai_pkl > 0.0) (nilai_lab + nilai_pkl) / 2.0 else nilai_lab
    val rataHadir: Double get() = (persentase_hadir + (100.0 - jam_terlambat)) / 2.0
    val rataDisiplin: Double get() = (100.0 - poin_pelanggaran + skor_sikap) / 2.0
}
