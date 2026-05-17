package com.teladan.amaliah.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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

        // 1. SETUP DEFAULT SPINNER (Penting agar tidak langsung terfilter hilang)
        val jurusanList = arrayOf("Semua Jurusan", "Farmasi", "Keperawatan", "TLM")
        val kelasList = arrayOf("Semua Kelas", "10", "11", "12")

        binding.spinnerFilterJurusan.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, jurusanList)
        binding.spinnerFilterKelas.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, kelasList)

        // 2. LISTENER SPINNER
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                processAndFilterData(currentDataList) // Hitung ulang hanya data yang sudah tersimpan di cache
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.spinnerFilterJurusan.onItemSelectedListener = spinnerListener
        binding.spinnerFilterKelas.onItemSelectedListener = spinnerListener

        // 3. INISIALISASI MATRIX LALU PANTAU DATA (OBSERVER)
        lifecycleScope.launch(Dispatchers.IO) {
            val matrixData = database.matrixDao().getMatrix() ?: KriteriaMatrixEntity()
            calculator = CalculatorFAHP(matrixData)

            withContext(Dispatchers.Main) {
                // Setelah FAHP siap, kita mulai memantau tabel siswa
                observeDataSiswa()
            }
        }
    }

    private fun observeDataSiswa() {
        // Menggunakan LiveData: Setiap ada data masuk/berubah di DB, fungsi ini otomatis jalan!
        database.siswaDao().getAllSiswaLive().observe(viewLifecycleOwner) { listSiswa ->
            currentDataList = listSiswa
            processAndFilterData(listSiswa)
        }
    }

    // FUNGSI UTAMA (FILTER -> HITUNG -> TAMPIL)
    private fun processAndFilterData(listSiswa: List<SiswaEntity>) {

        // Pengecekan 1: Apakah DB memang kosong?
        if (listSiswa.isEmpty()) {
            Log.e("RankingFragment", "INFO: Database tabel siswa_table KOSONG!")
            showEmptyState(true, "Belum ada data siswa di database.")
            rankingAdapter.setSiswaRanking(emptyList())
            return
        }

        // Ambil string dari Spinner UI
        val filterJurusan = binding.spinnerFilterJurusan.selectedItem?.toString() ?: "Semua"
        val filterKelas = binding.spinnerFilterKelas.selectedItem?.toString() ?: "Semua"

        // Deteksi apakah opsi "Semua" dipilih (Aman dari huruf besar/kecil seperti "semua kelas")
        val isSemuaJurusan = filterJurusan.contains("Semua", ignoreCase = true)
        val isSemuaKelas = filterKelas.contains("Semua", ignoreCase = true)

        // ================= LOGIKA KONDISIONAL FILTER ================= //
        val filteredList = if (isSemuaJurusan && isSemuaKelas) {
            // 1. Jika keduanya "Semua" -> Ambil semua data tanpa di-filter
            listSiswa
        } else if (isSemuaJurusan) {
            // 2. Jika Jurusan "Semua" -> Filter berdasarkan Kelas saja
            listSiswa.filter { it.tingkat_kelas == filterKelas }
        } else if (isSemuaKelas) {
            // 3. Jika Kelas "Semua" -> Filter berdasarkan Jurusan saja
            listSiswa.filter { it.jurusan == filterJurusan }
        } else {
            // 4. Jika keduanya spesifik -> Filter berdasarkan dua-duanya
            listSiswa.filter { it.jurusan == filterJurusan && it.tingkat_kelas == filterKelas }
        }
        // ============================================================= //

        // Pengecekan 2: Apakah ada data yang lolos Filter?
        if (filteredList.isEmpty()) {
            Log.w("RankingFragment", "INFO: Data kosong karena terfilter. Jurusan: $filterJurusan, Kelas: $filterKelas")
            showEmptyState(true, "Tidak ada data untuk kombinasi filter tersebut.")
            rankingAdapter.setSiswaRanking(emptyList())
            return
        }

        showEmptyState(false, "")

        // Pengecekan 3: Kalkulasi FAHP & Sorting Berdasarkan Skor
        lifecycleScope.launch(Dispatchers.Default) {
            try {
                val listRanking = filteredList.map { siswa ->
                    // Proteksi keamanan nilai
                    val rataAkd = if (siswa.rataAkademik.isNaN()) 0.0 else siswa.rataAkademik
                    val rataPrk = if (siswa.rataPraktik.isNaN()) 0.0 else siswa.rataPraktik
                    val rataHdr = if (siswa.rataHadir.isNaN()) 0.0 else siswa.rataHadir
                    val rataDsp = if (siswa.rataDisiplin.isNaN()) 0.0 else siswa.rataDisiplin

                    // Hitung Skor Akhir
                    val skorFAHP = calculator.calculateFinalScore(rataAkd, rataPrk, rataHdr, rataDsp)

                    SiswaRanking(siswa, if (skorFAHP.isNaN()) 0.0 else skorFAHP)

                }.sortedByDescending { it.skorAkhir } // DIURUTKAN SECARA OTOMATIS BERDASARKAN SKOR TERTINGGI

                // Kirim hasil ke Adapter di UI Thread
                withContext(Dispatchers.Main) {
                    rankingAdapter.setSiswaRanking(listRanking)
                    Log.d("RankingFragment", "Sukses render ${listRanking.size} data ke RecyclerView.")
                }
            } catch (e: Exception) {
                Log.e("RankingFragment", "Error perhitungan FAHP: ${e.localizedMessage}", e)
            }
        }
    }

    // Fungsi Pembantu Tampilan Empty State
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
