package com.teladan.amaliah.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teladan.amaliah.data.local.entity.SiswaEntity
import com.teladan.amaliah.databinding.ItemSiswaBinding
import com.teladan.amaliah.ui.siswa.AddSiswaActivity

class SiswaAdapter(
    private val onDeleteClick: (SiswaEntity) -> Unit
) : RecyclerView.Adapter<SiswaAdapter.SiswaViewHolder>() {

    private val siswaList = mutableListOf<SiswaEntity>()

    // Fungsi untuk me-refresh data
    fun setSiswa(list: List<SiswaEntity>) {
        siswaList.clear()
        siswaList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SiswaViewHolder(private val binding: ItemSiswaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(siswa: SiswaEntity) {
            binding.tvNamaSiswa.text = siswa.nama
            binding.tvNis.text = "NIS: ${siswa.nis}"
            binding.tvJurusan.text = siswa.jurusan
            
            // Klik item untuk Edit
            binding.root.setOnClickListener {
                val intent = Intent(it.context, AddSiswaActivity::class.java)
                intent.putExtra("EXTRA_SISWA_ID", siswa.id)
                it.context.startActivity(intent)
            }
            
            // Klik tombol Hapus
            binding.btnDelete.setOnClickListener {
                onDeleteClick(siswa)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiswaViewHolder {
        val binding = ItemSiswaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SiswaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SiswaViewHolder, position: Int) {
        holder.bind(siswaList[position])
    }

    override fun getItemCount(): Int = siswaList.size
}