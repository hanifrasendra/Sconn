package com.example.sconn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.sconn.data_class.RegisterRequest
import com.example.sconn.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch


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
            val email = regisBinding.etEmail.text.toString().trim()

            // 1. Validasi Input Sederhana
            if (fullName.isEmpty()) {
                regisBinding.etFullName.error = "Full name cannot be empty"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                regisBinding.etEmail.error = "Email address cannot be empty"
                return@setOnClickListener
            }

            sendDataToEndpoint(fullName, email)
        }
    }

    private fun sendDataToEndpoint(fullName: String, email: String) {
        regisBinding.btnRegister.isEnabled = false
        regisBinding.btnRegister.text = "Sending..."

        viewLifecycleOwner.lifecycleScope.launch{
            try{
                val request = RegisterRequest(id, fullname = fullName, email = email)


            } catch (e: Exception) {

            }
        }
    }
}