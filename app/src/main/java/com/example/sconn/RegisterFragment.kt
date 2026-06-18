package com.example.sconn

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.sconn.databaseLite.DatabaseHelper
import com.example.sconn.databinding.FragmentRegisterBinding


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val regisBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return regisBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        regisBinding.btnRegister.setOnClickListener {
            val fullName = regisBinding.etFullName.text.toString().trim()
            val major = regisBinding.etMajorStudy.text.toString().trim()
            val associate = regisBinding.etAssociate.text.toString().trim()
            val email = regisBinding.etEmail.text.toString().trim()
            val password = regisBinding.etPassword.text.toString().trim()

            // 1. Validasi Input Sederhana
            if (fullName.isEmpty()) {
                regisBinding.fullNameLabel.error = "Full name cannot be empty"
                return@setOnClickListener
            }
            if (major.isEmpty()) {
                Toast.makeText(requireContext(), "Major study cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (associate.isEmpty()) {
                Toast.makeText(requireContext(), "Associate/University cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                regisBinding.emailLabel.error = "Email address cannot be empty"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                regisBinding.passwordLabel.error = "Password cannot be empty"
                return@setOnClickListener
            }

            sendDataToEndpoint(fullName, major, associate, email, password)
        }
    }

    private fun sendDataToEndpoint(fullName: String, major: String, associate: String, email: String, password: String) {
        regisBinding.btnRegister.isEnabled = false
        regisBinding.btnRegister.text = "Sending..."

        val dbHelper = DatabaseHelper.getInstance(requireContext())
        val db = dbHelper.writableDatabase
        val value = ContentValues().apply {
            put("fullname", fullName)
            put("major", major)
            put("associate", associate)
            put("email", email)
            put("password", password)
        }
        val result = db.insert("users", null, value)
        if (result != -1L) {
            Toast.makeText(requireContext(), "Register berhasil!", Toast.LENGTH_SHORT).show()
            regisBinding.btnRegister.text = "REGIS"
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        } else {
            // gagal — email sudah ada, dll
            regisBinding.btnRegister.isEnabled = true
            regisBinding.btnRegister.text = "REGIS"
            Toast.makeText(requireContext(), "Register gagal!", Toast.LENGTH_SHORT).show()
        }
    }
}