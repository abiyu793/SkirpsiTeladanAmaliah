package com.teladan.amaliah.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.teladan.amaliah.data.local.AppDatabase
import com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity
import com.teladan.amaliah.databinding.FragmentDashboardBinding
import com.teladan.amaliah.helper.CalculatorFAHP
import com.teladan.amaliah.helper.SessionManager
import com.teladan.amaliah.ui.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!  // ← Baris 25

    private lateinit var sessionManager: SessionManager
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root  // ← Baris 34
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        database = AppDatabase.getDatabase(requireContext())

        // Tampilkan Nama Admin
        val adminName = sessionManager.getAdminName() ?: "Admin"
        binding.tvAdminName.text = adminName

        // Live Data Real-time Total Siswa
        database.siswaDao().getCountSiswa().observe(viewLifecycleOwner) { total ->
            binding.tvTotalSiswa.text = total.toString()
        }

        // ========== TOMBOL LOGOUT ==========
        // Pastikan btnLogout ada di layout
        try {
            binding.btnLogout.setOnClickListener {
                showLogoutDialog()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setupPieChart()
        observeSiswaDataForChart()
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar dari akun ini?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Ya") { dialog, _ ->
                dialog.dismiss()
                performLogout()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun performLogout() {
        sessionManager.logout()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setupPieChart() {
        binding.pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            transparentCircleRadius = 55f
            holeRadius = 40f
            setDrawCenterText(true)
            centerText = "Kelayakan"
            setCenterTextSize(14f)
            setCenterTextColor(Color.parseColor("#333333"))
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(11f)
            
            legend.apply {
                verticalAlignment = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
                orientation = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
                textSize = 12f
            }
        }
    }

    private fun observeSiswaDataForChart() {
        database.siswaDao().getAllSiswaLive().observe(viewLifecycleOwner) { siswaList ->
            if (siswaList.isNotEmpty()) {
                calculateAndRenderChart(siswaList)
            } else {
                binding.pieChart.clear()
            }
        }
    }

    private fun calculateAndRenderChart(siswaList: List<com.teladan.amaliah.data.local.entity.SiswaEntity>) {
        lifecycleScope.launch(Dispatchers.IO) {
            val matrixData = database.matrixDao().getMatrix() ?: KriteriaMatrixEntity()
            val calculator = CalculatorFAHP(matrixData)

            var sangatLayak = 0
            var layak = 0
            var cukupLayak = 0

            for (siswa in siswaList) {
                val finalScore = calculator.calculateFinalScore(
                    siswa.rataAkademik,
                    siswa.rataPraktik,
                    siswa.rataHadir,
                    siswa.rataDisiplin
                )

                if (finalScore > 90) {
                    sangatLayak++
                } else if (finalScore >= 80.0) {
                    layak++
                } else {
                    cukupLayak++
                }
            }

            val entries = ArrayList<PieEntry>()
            if (sangatLayak > 0) entries.add(PieEntry(sangatLayak.toFloat(), "Sangat Layak"))
            if (layak > 0) entries.add(PieEntry(layak.toFloat(), "Layak"))
            if (cukupLayak > 0) entries.add(PieEntry(cukupLayak.toFloat(), "Cukup Layak"))

            val colors = ArrayList<Int>()
            if (sangatLayak > 0) colors.add(Color.parseColor("#4CAF50")) // Hijau
            if (layak > 0) colors.add(Color.parseColor("#2196F3")) // Biru
            if (cukupLayak > 0) colors.add(Color.parseColor("#FF9800")) // Oranye

            val dataSet = PieDataSet(entries, "")
            dataSet.colors = colors
            dataSet.sliceSpace = 3f
            dataSet.selectionShift = 5f

            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter(binding.pieChart))
            data.setValueTextSize(12f)
            data.setValueTextColor(Color.WHITE)

            withContext(Dispatchers.Main) {
                binding.pieChart.data = data
                binding.pieChart.invalidate()
                binding.pieChart.animateY(1400, Easing.EaseInOutQuad)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}