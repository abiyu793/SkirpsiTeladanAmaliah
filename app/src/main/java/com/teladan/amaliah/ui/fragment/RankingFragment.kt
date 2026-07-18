package com.teladan.amaliah.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import com.teladan.amaliah.data.local.AppDatabase
import com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity
import com.teladan.amaliah.data.local.entity.SiswaEntity
import com.teladan.amaliah.databinding.FragmentRankingBinding
import com.teladan.amaliah.helper.CalculatorFAHP
import com.teladan.amaliah.helper.PreferenceHelper
import com.teladan.amaliah.ui.adapter.RankingAdapter
import com.teladan.amaliah.ui.adapter.SiswaRanking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RankingFragment : Fragment() {
    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RankingViewModel by viewModels()
    private lateinit var rankingAdapter: RankingAdapter
    
    // Simpan list terbaru untuk export PDF
    private var currentFilteredList: List<SiswaEntity> = emptyList()
    private var isDialogShowing = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // Pengecekan dialihkan ke dalam observer LiveData
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rankingAdapter = RankingAdapter()
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = rankingAdapter

        // Setup tombol export PDF
        binding.btnExportPDF.setOnClickListener {
            exportToPDF()
        }

        // 1. SETUP DEFAULT SPINNER
        val jurusanList = arrayOf("Semua", "LPFKK", "LPKPC", "LPLM")
        val kelasList = arrayOf("Semua", "10", "11", "12")

        binding.spinnerFilterJurusan.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, jurusanList)
        binding.spinnerFilterKelas.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, kelasList)

        // 2. LISTENER SPINNER -> Kirim ke ViewModel
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.setFilterJurusan(binding.spinnerFilterJurusan.selectedItem.toString())
                viewModel.setFilterKelas(binding.spinnerFilterKelas.selectedItem.toString())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.spinnerFilterJurusan.onItemSelectedListener = spinnerListener
        binding.spinnerFilterKelas.onItemSelectedListener = spinnerListener

        // 3. OBSERVE LIVEDATA DARI VIEWMODEL
        viewModel.filteredRankingList.observe(viewLifecycleOwner) { listSiswa ->
            currentFilteredList = listSiswa
            if (listSiswa.isEmpty()) {
                showEmptyState(true, "Tidak ada data untuk filter yang dipilih.")
                rankingAdapter.setSiswaRanking(emptyList())
            } else {
                showEmptyState(false, "")
                
                // Cek apakah ada data siswa dengan status kotor (is_dirty == true)
                val hasUncalculatedData = listSiswa.any { it.is_dirty }
                
                if (hasUncalculatedData) {
                    binding.tvWarningUpdate.visibility = View.VISIBLE
                    if (!isDialogShowing) {
                        isDialogShowing = true
                        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Perbaruan Data Diperlukan")
                            .setMessage("Terjadi perubahan data siswa. Ingin hitung ulang peringkat sekarang?")
                            .setPositiveButton("Hitung Sekarang") { dialog, _ ->
                                isDialogShowing = false
                                viewModel.calculateAndRefreshRanking()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Nanti Saja") { dialog, _ ->
                                isDialogShowing = false
                                dialog.dismiss()
                            }
                            .show()
                    }
                } else {
                    binding.tvWarningUpdate.visibility = View.GONE
                }
                
                // Konversi SiswaEntity menjadi SiswaRanking untuk Adapter
                val listRanking = listSiswa.map { siswa ->
                    SiswaRanking(siswa, siswa.skor_akhir)
                }
                rankingAdapter.setSiswaRanking(listRanking)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvRanking.visibility = View.GONE
                binding.tvEmptyState.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                // rvRanking & emptyState diurus oleh observer filteredRankingList
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { msg ->
            msg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        // 4. TOMBOL REFRESH
        binding.btnRefresh.setOnClickListener {
            viewModel.calculateAndRefreshRanking()
        }
    }

    private fun showEmptyState(show: Boolean, message: String) {
        if (show) {
            binding.rvRanking.visibility = View.GONE
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.tvEmptyState.text = message
        } else {
            binding.rvRanking.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.GONE
        }
    }

    // ======================================================= //
    // EKSPOR KE PDF
    // ======================================================= //
    private fun exportToPDF() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listRanking = currentFilteredList.map { siswa ->
                    Pair(siswa, siswa.skor_akhir)
                }

                if (listRanking.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Tidak ada data siswa untuk diekspor", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val filterJurusan = binding.spinnerFilterJurusan.selectedItem?.toString() ?: "Semua"
                val filterKelas = binding.spinnerFilterKelas.selectedItem?.toString() ?: "Semua"

                // Buat file PDF
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val fileName = "ranking_${filterJurusan}_${filterKelas}_$timeStamp.pdf"
                val pdfFile = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)

                val writer = PdfWriter(pdfFile)
                val pdfDocument = PdfDocument(writer)
                val document = Document(pdfDocument, PageSize.A4.rotate())
                document.setMargins(20f, 20f, 20f, 20f)

                // ==================== HEADER ====================
                val title = Paragraph("LAPORAN HASIL RANKING SISWA TELADAN")
                    .setFontSize(18f)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10f)
                document.add(title)

                val subtitle = Paragraph("Metode Fuzzy AHP (Chang, 1996)")
                    .setFontSize(12f)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20f)
                document.add(subtitle)

                val filterInfo = Paragraph("Filter: Jurusan = $filterJurusan | Kelas = $filterKelas")
                    .setFontSize(10f)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20f)
                document.add(filterInfo)

                val dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale("id", "ID"))
                val dateInfo = Paragraph("Dicetak: ${dateFormat.format(Date())}")
                    .setFontSize(10f)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20f)
                document.add(dateInfo)

                // ==================== TABEL RANKING ====================
                val table = Table(UnitValue.createPercentArray(floatArrayOf(5f, 10f, 15f, 12f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 8f, 10f)))
                table.setWidth(UnitValue.createPercentValue(100f))

                // Header tabel
                val headers = arrayOf(
                    "No", "NIS", "Nama", "Jurusan", "Kelas",
                    "Rapor", "Teori", "Lab", "PKL", "Hadir", "Terlambat", "Pelanggaran", "Sikap", "Skor"
                )

                headers.forEach { header ->
                    val cell = Cell()
                        .add(Paragraph(header).setFontSize(9f).setBold())
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setPadding(4f)
                    table.addCell(cell)
                }

                // Isi data ranking
                listRanking.forEachIndexed { index, pair ->
                    val siswa = pair.first
                    val skor = pair.second

                    table.addCell(createCell((index + 1).toString(), TextAlignment.CENTER))
                    table.addCell(createCell(siswa.nis, TextAlignment.CENTER))
                    table.addCell(createCell(siswa.nama, TextAlignment.LEFT))
                    table.addCell(createCell(siswa.jurusan, TextAlignment.CENTER))
                    table.addCell(createCell(siswa.tingkat_kelas, TextAlignment.CENTER))
                    table.addCell(createCell(siswa.nilai_rapor.toString(), TextAlignment.CENTER))
                    table.addCell(createCell(siswa.nilai_teori.toString(), TextAlignment.CENTER))
                    table.addCell(createCell(siswa.nilai_lab.toString(), TextAlignment.CENTER))
                    table.addCell(createCell(siswa.nilai_pkl.toString(), TextAlignment.CENTER))
                    table.addCell(createCell(siswa.persentase_hadir.toString(), TextAlignment.CENTER))
                    table.addCell(createCell(siswa.jam_terlambat.toString(), TextAlignment.CENTER))
                    table.addCell(createCell(siswa.poin_pelanggaran.toString(), TextAlignment.CENTER))
                    table.addCell(createCell(siswa.skor_sikap.toString(), TextAlignment.CENTER))

                    val skorCell = Cell()
                        .add(Paragraph(String.format("%.2f", skor)).setFontSize(9f).setBold())
                        .setTextAlignment(TextAlignment.CENTER)
                        .setPadding(4f)
                    table.addCell(skorCell)
                }

                document.add(table)

                // ==================== FOOTER ====================
                val totalData = Paragraph("Total Data: ${listRanking.size} siswa")
                    .setFontSize(10f)
                    .setMarginTop(20f)
                    .setTextAlignment(TextAlignment.RIGHT)
                document.add(totalData)

                val signature = Paragraph("\n\n\nMengetahui,\n\nKepala Sekolah")
                    .setFontSize(10f)
                    .setMarginTop(30f)
                    .setTextAlignment(TextAlignment.RIGHT)
                document.add(signature)

                document.close()

                // ==================== SHARE PDF ====================
                withContext(Dispatchers.Main) {
                    val uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
                        pdfFile
                    )

                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "application/pdf"
                        putExtra(Intent.EXTRA_STREAM, uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }

                    startActivity(Intent.createChooser(shareIntent, "Ekspor Ranking PDF"))
                    Toast.makeText(requireContext(), "PDF berhasil dibuat!\n${listRanking.size} data", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                Log.e("ExportPDF", "Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun createCell(text: String, alignment: TextAlignment): Cell {
        return Cell()
            .add(Paragraph(text).setFontSize(9f))
            .setTextAlignment(alignment)
            .setPadding(4f)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}