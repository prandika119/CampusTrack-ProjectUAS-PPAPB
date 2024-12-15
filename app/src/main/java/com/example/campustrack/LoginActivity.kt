package com.example.campustrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.campustrack.databinding.ActivityLoginBinding
import com.example.campustrack.model.user.User
import com.example.campustrack.model.user.UserRequest
import com.example.campustrack.model.user.UserResponse
import com.example.campustrack.network.ApiClient
import com.example.campustrack.network.UserApi
import com.example.campustrack.sharePreferences.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = SharedPreferencesManager(this)
        with(binding){
            tvHaventAccount.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
            btnLogin.setOnClickListener {
                onLoginClicked()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun onLoginClicked() {
        var email: String
        var password: String
        binding.apply {
            email = etEmail.text.toString()
            password = etPassword.text.toString()
        }
        loginUser(email, password)
    }

    private fun loginUser(email: String, password: String){
        Toast.makeText(this@LoginActivity, "mohon menunggu sebentar", Toast.LENGTH_SHORT).show()
        val retrofit = ApiClient.getInsance().create(UserApi::class.java)
        retrofit.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null){
                        for (i in data!!) {
                            if (i.email == email) {
                                if (i.password == password) {
                                    val id = i.id
                                    val name = i.name
                                    val email = i.email
                                    val prodi = i.prodi
                                    val angkatan = i.angkatan
                                    prefManager.saveId(id)
                                    prefManager.saveName(name)
                                    prefManager.saveEmail(email)
                                    prefManager.saveProdi(prodi)
                                    prefManager.saveAngkatan(angkatan)
                                    checkLoginStatus()
                                } else {
                                    Toast.makeText(this@LoginActivity, "Usernama atau Password Salah", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(p0: Call<List<User>>, p1: Throwable) {
                Toast.makeText(this@LoginActivity, "Koneksi Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkLoginStatus() {
        val email = prefManager.getEmail()
        if (email!!.isNotEmpty()) {
            startActivity(
                Intent(this@LoginActivity, MainActivity::class.java)
            )
            finish()
        }
    }

}
