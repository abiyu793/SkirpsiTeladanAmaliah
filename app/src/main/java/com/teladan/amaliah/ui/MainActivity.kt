package com.teladan.amaliah.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.teladan.amaliah.R
import com.teladan.amaliah.databinding.ActivityMainBinding
import com.teladan.amaliah.ui.fragment.DashboardFragment
import com.teladan.amaliah.ui.fragment.RankingFragment
import com.teladan.amaliah.ui.fragment.SiswaFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan Dashboard secara default ketika aplikasi baru dibuka
        if (savedInstanceState == null) {
            replaceFragment(DashboardFragment())
        }

        // Logic berpindah Fragment menggunakan Bottom Navigation
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    replaceFragment(DashboardFragment())
                    true
                }
                R.id.nav_siswa -> {
                    replaceFragment(SiswaFragment())
                    true
                }
                R.id.nav_ranking -> {
                    replaceFragment(RankingFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Fungsi helper untuk mengganti Fragment di dalam FragmentContainerView
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
