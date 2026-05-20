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

    private lateinit var database: AppDatabase
    private lateinit var rankingAdapter: RankingAdapter
    private lateinit var calculator: CalculatorFAHP

    // Penampung data agar saat dropdown diubah, kita tidak perlu query DB lagi
    private var currentDataList: List<SiswaEntity> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = AppDatabase.getDatabase(requireContext())

        rankingAdapter = RankingAdapter()
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = rankingAdapter

        // Setup tombol export PDF
        binding.btnExportPDF.setOnClickListener {
            exportToPDF()
        }

        // 1. SETUP DEFAULT SPINNER (GUNAKAN "Semua" BUKAN "Semua Jurusan/Kelas")
        val jurusanList = arrayOf("Semua", "Farmasi", "Keperawatan", "TLM")
        val kelasList = arrayOf("Semua", "10", "11", "12")

        binding.spinnerFilterJurusan.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, jurusanList)
        binding.spinnerFilterKelas.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, kelasList)

        // 2. LISTENER SPINNER
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                processAndFilterData(currentDataList)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.spinnerFilterJurusan.onItemSelectedListener = spinnerListener
        binding.spinnerFilterKelas.onItemSelectedListener = spinnerListener

        // 3. INISIALISASI MATRIX LALU PANTAU DATA
        lifecycleScope.launch(Dispatchers.IO) {
            val matrixData = database.matrixDao().getMatrix() ?: KriteriaMatrixEntity()
            calculator = CalculatorFAHP(matrixData)

            withContext(Dispatchers.Main) {
                observeDataSiswa()
            }
        }
    }

    private fun observeDataSiswa() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listSiswa = database.siswaDao().getAllSiswa()

                // === LOG UNTUK DEBUG ===
                Log.d("RankingFragment", "========== DATA DARI DATABASE ==========")
                Log.d("RankingFragment", "Total data siswa: ${listSiswa.size}")

                if (listSiswa.isNotEmpty()) {
                    val pertama = listSiswa.first()
                    Log.d("RankingFragment", "Contoh data pertama:")
                    Log.d("RankingFragment", "  Nama : ${pertama.nama}")
                    Log.d("RankingFragment", "  Jurusan : ${pertama.jurusan}")
                    Log.d("RankingFragment", "  Kelas : ${pertama.tingkat_kelas}")
                    Log.d("RankingFragment", "  rataAkademik : ${pertama.rataAkademik}")
                    Log.d("RankingFragment", "  rataPraktik : ${pertama.rataPraktik}")
                    Log.d("RankingFragment", "  rataHadir : ${pertama.rataHadir}")
                    Log.d("RankingFragment", "  rataDisiplin : ${pertama.rataDisiplin}")
                }
                Log.d("RankingFragment", "========================================")

                withContext(Dispatchers.Main) {
                    currentDataList = listSiswa
                    processAndFilterData(listSiswa)
                }
            } catch (e: Exception) {
                Log.e("RankingFragment", "Error loading data: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    // FUNGSI UTAMA (FILTER -> HITUNG -> TAMPIL)
    private fun processAndFilterData(listSiswa: List<SiswaEntity>) {
        if (listSiswa.isEmpty()) {
            showEmptyState(true, "Belum ada data siswa di database.")
            rankingAdapter.setSiswaRanking(emptyList())
            return
        }

        val filterJurusan = binding.spinnerFilterJurusan.selectedItem?.toString() ?: "Semua"
        val filterKelas = binding.spinnerFilterKelas.selectedItem?.toString() ?: "Semua"

        val filteredList = listSiswa.filter {
            (filterJurusan == "Semua" || it.jurusan == filterJurusan) &&
                    (filterKelas == "Semua" || it.tingkat_kelas == filterKelas)
        }

        if (filteredList.isEmpty()) {
            showEmptyState(true, "Tidak ada data untuk filter yang dipilih.")
            rankingAdapter.setSiswaRanking(emptyList())
            return
        }

        showEmptyState(false, "")

        lifecycleScope.launch(Dispatchers.Default) {
            try {
                val listRanking = filteredList.map { siswa ->
                    val rataAkd = if (siswa.rataAkademik.isNaN()) 0.0 else siswa.rataAkademik
                    val rataPrk = if (siswa.rataPraktik.isNaN()) 0.0 else siswa.rataPraktik
                    val rataHdr = if (siswa.rataHadir.isNaN()) 0.0 else siswa.rataHadir
                    val rataDsp = if (siswa.rataDisiplin.isNaN()) 0.0 else siswa.rataDisiplin

                    val skorFAHP = calculator.calculateFinalScore(rataAkd, rataPrk, rataHdr, rataDsp)

                    // Log untuk 5 data pertama
                    if (filteredList.indexOf(siswa) < 5) {
                        Log.d("RankingFragment", "Hitung: ${siswa.nama} -> Skor: $skorFAHP")
                    }

                    SiswaRanking(siswa, if (skorFAHP.isNaN()) 0.0 else skorFAHP)
                }.sortedByDescending { it.skorAkhir }

                withContext(Dispatchers.Main) {
                    Log.d("RankingFragment", "Final ranking: ${listRanking.size} data")
                    rankingAdapter.setSiswaRanking(listRanking)
                }
            } catch (e: Exception) {
                Log.e("RankingFragment", "Error perhitungan FAHP: ${e.localizedMessage}", e)
            }
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
                // Ambil matriks dari database
                val matrixData = database.matrixDao().getMatrix() ?: KriteriaMatrixEntity()
                val calculator = CalculatorFAHP(matrixData)

                // Ambil semua data siswa
                val listSiswa = database.siswaDao().getAllSiswa()

                // Log untuk debugging
                Log.d("ExportPDF", "Total siswa di database: ${listSiswa.size}")

                if (listSiswa.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Tidak ada data siswa untuk diekspor", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                // Ambil filter yang dipilih
                val filterJurusan = binding.spinnerFilterJurusan.selectedItem?.toString() ?: "Semua"
                val filterKelas = binding.spinnerFilterKelas.selectedItem?.toString() ?: "Semua"

                Log.d("ExportPDF", "Filter Jurusan: $filterJurusan, Filter Kelas: $filterKelas")

                // Filter data (sama seperti di processAndFilterData)
                val filteredList = listSiswa.filter {
                    (filterJurusan == "Semua" || it.jurusan == filterJurusan) &&
                            (filterKelas == "Semua" || it.tingkat_kelas == filterKelas)
                }

                Log.d("ExportPDF", "Data setelah filter: ${filteredList.size}")

                if (filteredList.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Tidak ada data untuk filter yang dipilih", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                // Hitung ranking untuk data yang sudah difilter
                val listRanking = filteredList.map { siswa ->
                    val rataAkd = (siswa.nilai_rapor + siswa.nilai_teori) / 2.0
                    val rataPrk = if (siswa.nilai_pkl > 0.0) (siswa.nilai_lab + siswa.nilai_pkl) / 2.0 else siswa.nilai_lab
                    val rataHdr = (siswa.persentase_hadir + (100.0 - siswa.jam_terlambat)) / 2.0
                    val rataDsp = (100.0 - siswa.poin_pelanggaran + siswa.skor_sikap) / 2.0

                    val skor = calculator.calculateFinalScore(rataAkd, rataPrk, rataHdr, rataDsp)
                    Pair(siswa, skor)
                }.sortedByDescending { it.second }

                Log.d("ExportPDF", "Data ranking: ${listRanking.size}")

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
                // Buat tabel dengan 14 kolom
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