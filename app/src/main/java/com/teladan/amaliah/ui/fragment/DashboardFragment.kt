package com.teladan.amaliah.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teladan.amaliah.data.local.AppDatabase
import com.teladan.amaliah.databinding.FragmentDashboardBinding
import com.teladan.amaliah.helper.SessionManager
import com.teladan.amaliah.ui.SettingKriteriaActivity
import com.teladan.amaliah.ui.login.LoginActivity

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

        // ========== TOMBOL PENGATURAN KRITERIA ==========
        // Pastikan btnSettingKriteria ada di layout
        try {
            binding.btnSettingKriteria.setOnClickListener {
                startActivity(Intent(requireContext(), SettingKriteriaActivity::class.java))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}