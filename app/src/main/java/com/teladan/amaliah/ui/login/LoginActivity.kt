package com.teladan.amaliah.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.teladan.amaliah.data.local.AppDatabase
import com.teladan.amaliah.databinding.ActivityLoginBinding
import com.teladan.amaliah.helper.SessionManager
import com.teladan.amaliah.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: AppDatabase
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        sessionManager = SessionManager(this)

        // Cek apakah sudah login
        if (sessionManager.isLoggedIn()) {
            navigateToMain()
            return  // Tambahkan return agar onCreate tidak lanjut
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty()) {
                binding.tilUsername.error = "Username tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.tilUsername.error = null
            }

            if (password.isEmpty()) {
                binding.tilPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.tilPassword.error = null
            }

            performLogin(username, password)
        }
    }

    private fun performLogin(username: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            var admin = database.adminDao().loginAdmin(username, password)

            // Fallback recovery jika database terhapus karena migrasi destruktif
            if (admin == null && username == "admin" && password == "123") {
                val defaultAdmin = com.teladan.amaliah.data.local.entity.Admin(
                    username = "admin",
                    password = "123",
                    nama_lengkap = "Administrator"
                )
                database.adminDao().insertAdmin(defaultAdmin)
                admin = defaultAdmin
            }

            withContext(Dispatchers.Main) {
                if (admin != null) {
                    sessionManager.saveLoginSession(admin.nama_lengkap)
                    Toast.makeText(this@LoginActivity, "Selamat Datang, ${admin.nama_lengkap}", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this@LoginActivity, "Username atau Password salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}