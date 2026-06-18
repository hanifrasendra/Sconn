package com.example.sconn

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.sconn.databaseLite.DatabaseHelper
import com.example.sconn.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val loginBinding get() = _binding!!

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        dbHelper = DatabaseHelper.getInstance(requireContext())

        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBinding.forgot.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        loginBinding.btnLogin.setOnClickListener {
            val email = loginBinding.email.text.toString().trim()
            val password = loginBinding.password.text.toString().trim()

            // 1. Validasi Input Sederhana
            if (email.isEmpty()) {
                loginBinding.email.error = "Email address cannot be empty"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                loginBinding.password.error = "Password cannot be empty"
            }

            val user = dbHelper.getUser(email)

            if (user != null) {
                val namaLengkap = user.fullname
                val idUser = user.id
                if (user.password == password) {
                    // [+] Simpan user_id, fullname, email ke SharedPreferences
                    val prefs = requireContext().getSharedPreferences("sconn_prefs", Context.MODE_PRIVATE)
                    prefs.edit()
                        .putInt("user_id", idUser)
                        .putString("fullname", namaLengkap)
                        .putString("email", user.email)
                        .apply()

                    Toast.makeText(requireContext(), "Halo, $namaLengkap!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Password anda salah!", Toast.LENGTH_SHORT).show()
                }


            } else {
                loginBinding.email.error = "Email tidak terdaftar"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Disarankan untuk menghindari memory leak pada fragment binding
    }


}