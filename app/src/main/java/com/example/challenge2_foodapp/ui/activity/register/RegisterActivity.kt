package com.example.challenge2_foodapp.ui.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.challenge2_foodapp.databinding.ActivityRegisterBinding
import com.example.challenge2_foodapp.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (getSharedPreferences("login", 0).getBoolean("isLogin", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            onRegister()
        }
    }

    private fun onRegister() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (validateForm()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("Register", "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        showToast("Registrasi gagal")
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            showToast("Registrasi berhasil")
            savedWithSharedPreferences()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun savedWithSharedPreferences() {

        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val phone = binding.etPhoneNumber.text.toString()

        val sharedPreferences = getSharedPreferences("myAccount", 0)
        val editor = sharedPreferences.edit()

        editor.putString("name", name)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("phone", phone)
        editor.apply()

        val isLogin = getSharedPreferences("login", 0)
        val loginEditor = isLogin.edit()
        loginEditor.putBoolean("isLogin", true)
        loginEditor.apply()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun validateForm(): Boolean {
        return if (binding.etName.text.toString().isEmpty()) {
            binding.etName.error = "Nama tidak boleh kosong"
            false
        } else if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = "Email tidak boleh kosong"
            false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
            binding.etEmail.error = "Email tidak valid"
            false
        } else if (binding.etPassword.text.toString().isEmpty()) {
            binding.etPassword.error = "Password tidak boleh kosong"
            false
        } else if (binding.etPassword.text.toString().length < 6) {
            binding.etPassword.error = "Password minimal 6 karakter"
            false
        } else if (binding.etRetypePassword.text.toString().isEmpty()) {
            binding.etRetypePassword.error = "Konfirmasi password tidak boleh kosong"
            false
        } else if (binding.etPassword.text.toString() != binding.etRetypePassword.text.toString()) {
            binding.etRetypePassword.error = "Password tidak sama"
            false
        } else {
            true
        }
    }

}