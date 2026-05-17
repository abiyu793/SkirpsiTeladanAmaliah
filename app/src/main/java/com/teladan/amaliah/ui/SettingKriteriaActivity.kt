package com.teladan.amaliah.ui

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.teladan.amaliah.data.local.AppDatabase
import com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity
import com.teladan.amaliah.databinding.ActivitySettingKriteriaBinding
import com.teladan.amaliah.helper.CalculatorFAHP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingKriteriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingKriteriaBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingKriteriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        loadCurrentMatrix()

        // Tombol Simpan
        binding.btnSimpan.setOnClickListener {
            saveMatrix()
        }

        // Tombol Reset ke Default
        binding.btnReset.setOnClickListener {
            resetToDefault()
        }
    }

    private fun loadCurrentMatrix() {
        lifecycleScope.launch(Dispatchers.IO) {
            val matrix = database.matrixDao().getMatrix() ?: KriteriaMatrixEntity()

            withContext(Dispatchers.Main) {
                binding.etPA.setText(matrix.praktik_vs_akademik.toString())
                binding.etDA.setText(matrix.disiplin_vs_akademik.toString())
                binding.etPH.setText(matrix.praktik_vs_hadir.toString())
                binding.etAH.setText(matrix.akademik_vs_hadir.toString())
                binding.etPD.setText(matrix.praktik_vs_disiplin.toString())
                binding.etHD.setText(matrix.hadir_vs_disiplin.toString())
            }
        }
    }

    private fun resetToDefault() {
        AlertDialog.Builder(this)
            .setTitle("Reset Matriks")
            .setMessage("Apakah Anda yakin ingin mengembalikan semua nilai matriks ke default?")
            .setPositiveButton("Ya") { _, _ ->
                binding.etPA.setText("5.0")
                binding.etDA.setText("3.0")
                binding.etPH.setText("7.0")
                binding.etAH.setText("1.0")
                binding.etPD.setText("1.0")
                binding.etHD.setText("1.0")
                Toast.makeText(this, "Nilai matriks telah direset ke default", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        // Fungsi validasi untuk setiap EditText
        fun validateEditText(text: String, fieldName: String): Boolean {
            val sanitizedText = text.replace(",", ".")
            val value = sanitizedText.toDoubleOrNull()
            if (value == null) {
                Toast.makeText(this, "$fieldName harus diisi dengan angka", Toast.LENGTH_SHORT).show()
                return false
            }
            if (value <= 0.0 || value > 9.0) {
                Toast.makeText(this, "$fieldName harus bernilai lebih dari 0 dan maksimal 9", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }

        if (!validateEditText(binding.etPA.text.toString(), "Praktik vs Akademik")) isValid = false
        if (!validateEditText(binding.etDA.text.toString(), "Disiplin vs Akademik")) isValid = false
        if (!validateEditText(binding.etPH.text.toString(), "Praktik vs Kehadiran")) isValid = false
        if (!validateEditText(binding.etAH.text.toString(), "Akademik vs Kehadiran")) isValid = false
        if (!validateEditText(binding.etPD.text.toString(), "Praktik vs Kedisiplinan")) isValid = false
        if (!validateEditText(binding.etHD.text.toString(), "Kehadiran vs Kedisiplinan")) isValid = false

        return isValid
    }

    private fun saveMatrix() {
        // 1. Validasi Input
        if (!validateInputs()) {
            return
        }

        // 2. Ambil nilai dari form
        val praktikVsAkademik = binding.etPA.text.toString().replace(",", ".").toDouble()
        val disiplinVsAkademik = binding.etDA.text.toString().replace(",", ".").toDouble()
        val praktikVsHadir = binding.etPH.text.toString().replace(",", ".").toDouble()
        val akademikVsHadir = binding.etAH.text.toString().replace(",", ".").toDouble()
        val praktikVsDisiplin = binding.etPD.text.toString().replace(",", ".").toDouble()
        val hadirVsDisiplin = binding.etHD.text.toString().replace(",", ".").toDouble()

        val newMatrix = KriteriaMatrixEntity(
            id = 1,
            praktik_vs_akademik = praktikVsAkademik,
            disiplin_vs_akademik = disiplinVsAkademik,
            praktik_vs_hadir = praktikVsHadir,
            akademik_vs_hadir = akademikVsHadir,
            praktik_vs_disiplin = praktikVsDisiplin,
            hadir_vs_disiplin = hadirVsDisiplin
        )

        // 3. Hitung Consistency Ratio
        val tempCalculator = CalculatorFAHP(newMatrix)
        val cr = tempCalculator.checkConsistencyRatio()
        val isConsistent = cr <= 0.1

        // 4. Tampilkan dialog peringatan jika CR > 0.1
        if (!isConsistent) {
            showConsistencyWarningDialog(newMatrix, cr)
        } else {
            // Matriks konsisten, langsung simpan
            saveMatrixToDatabase(newMatrix, cr)
        }
    }

    private fun showConsistencyWarningDialog(matrix: KriteriaMatrixEntity, cr: Double) {
        AlertDialog.Builder(this)
            .setTitle("⚠️ Peringatan Konsistensi")
            .setMessage(
                "Nilai Consistency Ratio (CR) = ${String.format("%.4f", cr)}\n\n" +
                        "Standar konsistensi yang dapat diterima adalah CR ≤ 0.1\n" +
                        "CR > 0.1 mengindikasikan bahwa matriks perbandingan tidak konsisten.\n\n" +
                        "Hal ini dapat mempengaruhi keakuratan hasil perankingan.\n\n" +
                        "Apakah Anda tetap ingin menyimpan matriks ini?"
            )
            .setPositiveButton("Tetap Simpan") { _, _ ->
                saveMatrixToDatabase(matrix, cr)
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun saveMatrixToDatabase(matrix: KriteriaMatrixEntity, cr: Double) {
        lifecycleScope.launch(Dispatchers.IO) {
            database.matrixDao().saveMatrix(matrix)
            withContext(Dispatchers.Main) {
                val status = if (cr <= 0.1) "Konsisten" else "Tidak Konsisten"
                Toast.makeText(
                    this@SettingKriteriaActivity,
                    "Matriks Berhasil Disimpan!\nCR = ${String.format("%.4f", cr)} ($status)",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }
}