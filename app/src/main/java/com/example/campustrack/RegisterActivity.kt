package com.example.campustrack

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.campustrack.databinding.ActivityRegisterBinding
import com.example.campustrack.model.user.UserRequest
import com.example.campustrack.model.user.UserResponse
import com.example.campustrack.network.ApiClient
import com.example.campustrack.network.UserApi
import com.example.campustrack.validation.RegistrationValidator
import com.example.campustrack.validation.ValidationResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RegisterActivity : AppCompatActivity() {
    private val registrationValidator = RegistrationValidator()
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            btnRegister.setOnClickListener{
                onRegisterClicked()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun onRegisterClicked() {
        binding.apply {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val name = etFullName.text.toString()

            when (val result = registrationValidator.validate(email, password, confirmPassword, name)) {
                is ValidationResult.Success -> {
                    // Lakukan proses registrasi
                    postUserApi()
                    Toast.makeText(this@RegisterActivity, "Proses membuat akun, harap menunggu", Toast.LENGTH_SHORT).show()

                }
                is ValidationResult.Error -> {
                    // Tampilkan pesan error
                    Toast.makeText(this@RegisterActivity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun postUserApi(){
        val email = binding.etEmail.text.toString()
        val name = binding.etFullName.text.toString()
        val prodi = binding.etProgramStudi.text.toString()
        val angkatan = binding.etTahunAngkatan.text.toString().toInt()
        val password = binding.etPassword.text.toString()
        var userRequest = UserRequest(name, email, password, prodi, angkatan)

        val retrofit = ApiClient.getInsance().create(UserApi::class.java)
        retrofit.createUser(userRequest).enqueue(object : Callback<UserResponse>{
            override fun onResponse(p0: Call<UserResponse>, response: Response<UserResponse>) {
                val message = response.body()!!.message
                Toast.makeText(this@RegisterActivity, "Berhasil Mendaftar, Silahkan Login", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

            }

            override fun onFailure(response: Call<UserResponse>, p1: Throwable) {
                Toast.makeText(this@RegisterActivity, "Gagal Mendaftarkan Akun", Toast.LENGTH_SHORT).show()

            }

        })
    }

}