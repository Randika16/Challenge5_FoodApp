package com.example.challenge2_foodapp.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.challenge2_foodapp.databinding.FragmentProfileBinding
import com.example.challenge2_foodapp.ui.activity.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isEnabledInput(false)

        getSavedData()
        binding.btnChecklist.setOnClickListener {
            savedWithSharedPreferences()
            isEnabledInput(false)
            visibilityButton(false, false, true)
            Toast.makeText(requireContext(), "Profile data saved", Toast.LENGTH_SHORT).show()
        }

        binding.btnCancel.setOnClickListener {
            isEnabledInput(false)
            visibilityButton(false, false, true)
        }

        binding.logoutButton.setOnClickListener {
            logoutConfirmation()
        }

        binding.btnEdit.setOnClickListener {
            isEnabledInput(true)
            visibilityButton(true, true, false)
        }
    }

    private fun logoutConfirmation() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                Firebase.auth.signOut()
                logout()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun visibilityButton(btnCancel: Boolean, btnChecklist: Boolean, btnEdit: Boolean) {
        binding.btnCancel.visibility = if (btnCancel) View.VISIBLE else View.GONE
        binding.btnChecklist.visibility = if (btnChecklist) View.VISIBLE else View.GONE
        binding.btnEdit.visibility = if (btnEdit) View.VISIBLE else View.GONE
    }

    private fun isEnabledInput(enable: Boolean) {
        binding.nameEdit.isEnabled = enable
        binding.phoneNumberEdit.isEnabled = enable
    }

    private fun logout() {
        val sharedPreferences = requireActivity().getSharedPreferences("myAccount", 0)
        val editor = sharedPreferences.edit()
        editor.remove("email")
        editor.remove("password")
        editor.apply()

        val login = requireActivity().getSharedPreferences("login", 0)
        val loginEditor = login.edit()
        loginEditor.putBoolean("isLogin", false)
        loginEditor.apply()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun savedWithSharedPreferences() {

        val username = binding.nameEdit.text.toString()
        val phone = binding.phoneNumberEdit.text.toString()

        val sharedPreferences = requireActivity().getSharedPreferences("myAccount", 0)
        val editor = sharedPreferences.edit()

        editor.putString("name", username)
        editor.putString("phone", phone)
        editor.apply()

    }

    private fun getSavedData() {
        val sharedPreferences = requireActivity().getSharedPreferences("myAccount", 0)
        val name = sharedPreferences.getString("name", "")
        val password = sharedPreferences.getString("password", "")
        val email = sharedPreferences.getString("email", "")
        val phone = sharedPreferences.getString("phone", "")

        binding.nameEdit.setText(name)
        binding.passwordEdit.setText(password)
        binding.emailEdit.setText(email)
        binding.phoneNumberEdit.setText(phone)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}