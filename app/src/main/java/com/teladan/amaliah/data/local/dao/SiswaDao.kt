package com.teladan.amaliah.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.teladan.amaliah.data.local.entity.SiswaEntity

@Dao
interface SiswaDao {

    // GET ALL SISWA (Mengabaikan yang sudah di soft delete)
    @Query("SELECT * FROM siswa_table WHERE is_deleted = 0 ORDER BY nama ASC")
    suspend fun getAllSiswa(): List<SiswaEntity>

    // GET SISWA BY ID (untuk edit)
    @Query("SELECT * FROM siswa_table WHERE id = :id LIMIT 1")
    suspend fun getSiswaById(id: Int): SiswaEntity?

    // INSERT SISWA (kembalikan ID)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSiswa(siswa: SiswaEntity): Long

    // UPDATE SISWA
    @Update
    suspend fun updateSiswa(siswa: SiswaEntity)

    // UPDATE SISWA MASSAL
    @Update
    suspend fun updateSiswaMassal(siswaList: List<SiswaEntity>)

    // DELETE SISWA (opsional)
    @Query("DELETE FROM siswa_table WHERE id = :id")
    suspend fun deleteSiswa(id: Int)
    
    // SOFT DELETE SISWA (Tandai dihapus dan kotor)
    @Query("UPDATE siswa_table SET is_deleted = 1, is_dirty = 1 WHERE id = :id")
    suspend fun softDeleteSiswa(id: Int)
    
    // PERMANENT DELETE (Hapus permanen setelah ranking dihitung)
    @Query("DELETE FROM siswa_table WHERE is_deleted = 1")
    suspend fun deletePermanently()

    // LIVE DATA untuk Real-time Dashboard (jumlah siswa aktif)
    @Query("SELECT COUNT(*) FROM siswa_table WHERE is_deleted = 0")
    fun getCountSiswa(): LiveData<Int>

    @Query("SELECT * FROM siswa_table WHERE is_deleted = 0 ORDER BY nama ASC")
    fun getAllSiswaLive(): LiveData<List<SiswaEntity>>

    // GET ALL SISWA UNTUK RANKING (LIVE)
    @Query("SELECT * FROM siswa_table WHERE is_deleted = 0 ORDER BY skor_akhir DESC")
    fun getAllSiswaSortedLive(): LiveData<List<SiswaEntity>>

    // FILTER berdasarkan jurusan dan kelas (untuk ranking)
    @Query("SELECT * FROM siswa_table WHERE jurusan = :jurusan AND tingkat_kelas = :kelas AND is_deleted = 0 ORDER BY nama ASC")
    suspend fun getSiswaByJurusanAndKelas(jurusan: String, kelas: String): List<SiswaEntity>

    // GET TOTAL SISWA (tanpa LiveData, untuk keperluan tertentu)
    @Query("SELECT COUNT(*) FROM siswa_table WHERE is_deleted = 0")
    suspend fun getTotalSiswa(): Int
}