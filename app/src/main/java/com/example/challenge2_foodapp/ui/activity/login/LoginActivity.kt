package com.example.challenge2_foodapp.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge2_foodapp.databinding.ActivityLoginBinding
import com.example.challenge2_foodapp.ui.activity.MainActivity
import com.example.challenge2_foodapp.ui.activity.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (getSharedPreferences("login", 0).getBoolean("isLogin", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            if (validateForm()) {
                onLogin(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateForm(): Boolean {
        return if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = "Email cannot be empty"
            false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                .matches()
        ) {
            binding.etEmail.error = "Email is not valid"
            false
        } else if (binding.etPassword.text.toString().isEmpty()) {
            binding.etPassword.error = "Password cannot be empty"
            false
        } else if (binding.etPassword.text.toString().length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            false
        } else {
            true
        }
    }

    private fun onLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    val user = auth.currentUser
                    savedLoginSharedPreferences()
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login Fail", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun savedLoginSharedPreferences() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        val sharedPreferences = getSharedPreferences("myAccount", 0)
        val editor = sharedPreferences.edit()

        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()

        val isLogin = getSharedPreferences("login", 0)
        val loginEditor = isLogin.edit()
        loginEditor.putBoolean("isLogin", true)
        loginEditor.apply()
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            showToast("Login berhasil")
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

}