package com.teladan.amaliah.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.teladan.amaliah.data.local.AppDatabase
import com.teladan.amaliah.data.local.entity.SiswaEntity
import com.teladan.amaliah.databinding.FragmentSiswaBinding
import com.teladan.amaliah.ui.siswa.AddSiswaActivity
import com.teladan.amaliah.helper.PreferenceHelper
import com.teladan.amaliah.ui.adapter.SiswaAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast

class SiswaFragment : Fragment() {

    private var _binding: FragmentSiswaBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: AppDatabase
    private lateinit var siswaAdapter: SiswaAdapter
    private var originalList = listOf<SiswaEntity>()  // ← Ubah ke SiswaEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSiswaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = AppDatabase.getDatabase(requireContext())
        setupRecyclerView()
        setupSearchAndSort()

        binding.fabAddSiswa.setOnClickListener {
            startActivity(Intent(requireContext(), AddSiswaActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadDataSiswa()
    }

    private fun setupRecyclerView() {
        siswaAdapter = SiswaAdapter { siswa ->
            showDeleteConfirmationDialog(siswa)
        }
        binding.rvSiswa.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = siswaAdapter
        }
    }

    private fun setupSearchAndSort() {
        val sortOptions = arrayOf("Nama (A-Z)", "Jurusan")
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            sortOptions
        )
        binding.spinnerSort.adapter = spinnerAdapter

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilterAndSort()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                applyFilterAndSort()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun applyFilterAndSort() {
        if (originalList.isEmpty()) return

        val query = binding.etSearch.text.toString().trim()
        val sortMode = binding.spinnerSort.selectedItem?.toString() ?: "Nama (A-Z)"

        var filteredList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.nama.contains(query, ignoreCase = true)
            }
        }

        filteredList = if (sortMode == "Jurusan") {
            filteredList.sortedBy { it.jurusan }
        } else {
            filteredList.sortedBy { it.nama }
        }

        if (filteredList.isEmpty()) {
            binding.rvSiswa.visibility = View.GONE
            binding.tvEmptyData.visibility = View.VISIBLE
        } else {
            binding.rvSiswa.visibility = View.VISIBLE
            binding.tvEmptyData.visibility = View.GONE
            siswaAdapter.setSiswa(filteredList)
        }
    }

    private fun loadDataSiswa() {
        lifecycleScope.launch(Dispatchers.IO) {
            originalList = database.siswaDao().getAllSiswa()

            withContext(Dispatchers.Main) {
                if (originalList.isEmpty()) {
                    binding.rvSiswa.visibility = View.GONE
                    binding.tvEmptyData.visibility = View.VISIBLE
                } else {
                    binding.rvSiswa.visibility = View.VISIBLE
                    binding.tvEmptyData.visibility = View.GONE
                    applyFilterAndSort()
                }
            }
        }
    }

    private fun showDeleteConfirmationDialog(siswa: SiswaEntity) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Hapus Data Siswa")
            .setMessage("Apakah Anda yakin ingin menghapus data ${siswa.nama}?")
            .setPositiveButton("Hapus") { _, _ ->
                deleteSiswa(siswa)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteSiswa(siswa: SiswaEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            database.siswaDao().softDeleteSiswa(siswa.id)
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Siswa berhasil dihapus", Toast.LENGTH_SHORT).show()
                loadDataSiswa() // Refresh data setelah dihapus
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}