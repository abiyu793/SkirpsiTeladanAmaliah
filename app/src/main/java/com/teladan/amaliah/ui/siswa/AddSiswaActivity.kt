package com.teladan.amaliah.ui.siswa

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.teladan.amaliah.data.local.AppDatabase
import com.teladan.amaliah.data.local.entity.SiswaEntity
import com.teladan.amaliah.databinding.ActivityAddSiswaBinding
import com.teladan.amaliah.helper.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddSiswaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSiswaBinding
    private lateinit var database: AppDatabase
    private var isEditMode = false
    private var editSiswaId = -1
    private var cachedSkorAkhir = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        // Setup Dropdown (Spinner) untuk Jurusan
        val jurusanList = arrayOf("LPFKK", "LPKPC", "LPLM")
        val jurusanAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, jurusanList)
        binding.spinnerJurusan.adapter = jurusanAdapter

        // Setup Dropdown untuk Kelas
        val kelasList = arrayOf("10", "11", "12")
        val kelasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, kelasList)
        binding.spinnerKelas.adapter = kelasAdapter

        // Cek apakah ini mode EDIT
        editSiswaId = intent.getIntExtra("EXTRA_SISWA_ID", -1)
        if (editSiswaId != -1) {
            isEditMode = true
            binding.btnSimpan.text = "UPDATE DATA"
            loadDataForEdit()
        }

        binding.btnSimpan.setOnClickListener {
            simpanDataSiswa()
        }
    }

    // ========== FUNGSI LOAD DATA SAAT EDIT ==========
    private fun loadDataForEdit() {
        lifecycleScope.launch(Dispatchers.IO) {
            val data = database.siswaDao().getSiswaById(editSiswaId)
            withContext(Dispatchers.Main) {
                data?.let {
                    // Simpan cache skor lama
                    cachedSkorAkhir = it.skor_akhir
                    
                    // Isi form profil siswa
                    binding.etNis.setText(it.nis)
                    binding.etNama.setText(it.nama)
                    binding.etTahunAjaran.setText(it.tahun_ajaran)

                    // Set Spinner Jurusan
                    val jurusanArray = arrayOf("LPFKK", "LPKPC", "LPLM")
                    val jurusanPos = jurusanArray.indexOf(it.jurusan)
                    if (jurusanPos >= 0) binding.spinnerJurusan.setSelection(jurusanPos)

                    // Set Spinner Kelas
                    val kelasArray = arrayOf("10", "11", "12")
                    val kelasPos = kelasArray.indexOf(it.tingkat_kelas)
                    if (kelasPos >= 0) binding.spinnerKelas.setSelection(kelasPos)

                    // Isi form nilai (sub-kriteria)
                    binding.etRapor.setText(it.nilai_rapor.toString())
                    binding.etTeori.setText(it.nilai_teori.toString())
                    binding.etLab.setText(it.nilai_lab.toString())
                    binding.etPkl.setText(it.nilai_pkl.toString())
                    binding.etHadir.setText(it.persentase_hadir.toString())
                    binding.etTerlambat.setText(it.jam_terlambat.toString())
                    binding.etPelanggaran.setText(it.poin_pelanggaran.toString())
                    binding.etSikap.setText(it.skor_sikap.toString())
                }
            }
        }
    }

    // ========== FUNGSI SIMPAN (INSERT ATAU UPDATE) ==========
    private fun simpanDataSiswa() {
        val nis = binding.etNis.text.toString().trim()
        val nama = binding.etNama.text.toString().trim()
        val jurusan = binding.spinnerJurusan.selectedItem.toString()
        val kelas = binding.spinnerKelas.selectedItem.toString()
        val tahunAjaran = binding.etTahunAjaran.text.toString().trim()

        // === VALIDASI WAJIB ===
        if (nis.isEmpty()) {
            binding.tilNis.error = "NIS tidak boleh kosong"
            return
        } else {
            binding.tilNis.error = null
        }

        if (nama.isEmpty()) {
            binding.tilNama.error = "Nama tidak boleh kosong"
            return
        } else {
            binding.tilNama.error = null
        }

        if (tahunAjaran.isEmpty()) {
            binding.etTahunAjaran.error = "Tahun Ajaran tidak boleh kosong"
            return
        }

        // === AMBIL NILAI SUB-KRITERIA (default 0 jika kosong) ===
        val rapor = binding.etRapor.text.toString().toDoubleOrNull() ?: 0.0
        val teori = binding.etTeori.text.toString().toDoubleOrNull() ?: 0.0
        val lab = binding.etLab.text.toString().toDoubleOrNull() ?: 0.0
        val pkl = binding.etPkl.text.toString().toDoubleOrNull() ?: 0.0
        val hadir = binding.etHadir.text.toString().toDoubleOrNull() ?: 0.0
        val terlambat = binding.etTerlambat.text.toString().toDoubleOrNull() ?: 0.0
        val pelanggaran = binding.etPelanggaran.text.toString().toDoubleOrNull() ?: 0.0
        val sikap = binding.etSikap.text.toString().toDoubleOrNull() ?: 0.0

        // === BUAT OBJEK SISWAENTITY ===
        val siswa = SiswaEntity(
            id = if (isEditMode) editSiswaId else 0,
            nis = nis,
            nama = nama,
            jurusan = jurusan,
            tingkat_kelas = kelas,
            tahun_ajaran = tahunAjaran,
            nilai_rapor = rapor,
            nilai_teori = teori,
            nilai_lab = lab,
            nilai_pkl = pkl,
            persentase_hadir = hadir,
            jam_terlambat = terlambat,
            poin_pelanggaran = pelanggaran,
            skor_sikap = sikap,
            skor_akhir = cachedSkorAkhir,
            is_dirty = true
        )

        // === SIMPAN KE DATABASE ===
        lifecycleScope.launch(Dispatchers.IO) {
            if (isEditMode) {
                database.siswaDao().updateSiswa(siswa)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddSiswaActivity, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                database.siswaDao().insertSiswa(siswa)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddSiswaActivity, "Data Siswa Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}