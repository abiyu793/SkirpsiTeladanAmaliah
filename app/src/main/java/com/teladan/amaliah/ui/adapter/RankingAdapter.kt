package com.teladan.amaliah.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teladan.amaliah.data.local.entity.SiswaEntity
import com.teladan.amaliah.databinding.ItemRankingBinding

// Data class untuk ranking (pakai SiswaEntity)
data class SiswaRanking(val data: SiswaEntity, val skorAkhir: Double)

class RankingAdapter : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    private val rankingList = mutableListOf<SiswaRanking>()

    fun setSiswaRanking(list: List<SiswaRanking>) {
        rankingList.clear()
        rankingList.addAll(list)
        notifyDataSetChanged()
    }

    inner class RankingViewHolder(private val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SiswaRanking, position: Int) {
            binding.tvRankNumber.text = "#${position + 1}"

            // Akses data dari SiswaEntity
            binding.tvNama.text = item.data.nama
            binding.tvJurusan.text = "NIS: ${item.data.nis} • ${item.data.jurusan} • Kelas ${item.data.tingkat_kelas}"
            binding.tvSkor.text = String.format(java.util.Locale.US, "%.2f", item.skorAkhir)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding = ItemRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.bind(rankingList[position], position)
    }

    override fun getItemCount(): Int = rankingList.size
}