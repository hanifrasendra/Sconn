package com.example.sconn

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.sconn.databaseLite.DatabaseHelper
import com.example.sconn.databinding.FragmentAddSessionBinding

class AddSessionFragment : Fragment() {
    private var _binding: FragmentAddSessionBinding? = null
    private val sessionBinding get() = _binding!!

    private lateinit var dbHelper: DatabaseHelper
    private var photoPath = ""


    // Launcher untuk buka gallery
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            photoPath = it.toString()
            sessionBinding.imgUploadPreview.setImageURI(it)
            sessionBinding.imgUploadPreview.clearColorFilter() // hapus tint
            sessionBinding.tvUploadLabel.visibility = View.GONE // sembunyiin label
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddSessionBinding.inflate(inflater, container, false)
        dbHelper = DatabaseHelper.getInstance(requireContext())
        return sessionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Klik card upload → buka gallery
        sessionBinding.cardUpload.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        sessionBinding.btnCreate.setOnClickListener {
            val sessionTitle = sessionBinding.etSessionTitle.text.toString().trim()
            val subject = sessionBinding.etSubject.text.toString().trim()
            val date = sessionBinding.etSessionDate.text.toString().trim()
            val time = sessionBinding.etSessionTime.text.toString().trim()
            val placeName = sessionBinding.etPlaceName.text.toString().trim()
            val fullAddress = sessionBinding.etFullAddress.text.toString().trim()
            val location = sessionBinding.etLocation.text.toString().trim()
            val description = sessionBinding.etDescription.text.toString().trim()

            // Validasi
            if (sessionTitle.isEmpty()) {
                sessionBinding.etSessionTitle.error = "Session title is required"
                return@setOnClickListener
            }
            if (subject.isEmpty() || subject == "Select a subject") {
                Toast.makeText(requireContext(), "Please select a subject", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (date.isEmpty()) {
                sessionBinding.etSessionDate.error = "Date is required"
                return@setOnClickListener
            }
            if (time.isEmpty()) {
                sessionBinding.etSessionTime.error = "Time is required"
                return@setOnClickListener
            }

            // Ambil user_id dari SharedPreferences
            // Disamakan dengan LoginFragment: "sconn_prefs" dan "user_id"
            val prefs = requireContext().getSharedPreferences("sconn_prefs", Context.MODE_PRIVATE)
            val userId = prefs.getInt("user_id", -1)

            // Cek kalau belum login
            if (userId == -1) {
                Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Put value untuk setiap variable input
            val values = ContentValues().apply {
                put("user_id", userId)
                put("title", sessionTitle)
                put("subject", subject)
                put("date", date)
                put("time", time)
                put("place_name", placeName)
                put("full_address", fullAddress)
                put("location", location)
                put("photo_path", photoPath)
                put("description", description)
            }

            // Insert ke DB menggunakan DatabaseHelper
            val db = dbHelper.writableDatabase
            val result = db.insert("sessions", null, values)

            if (result != -1L) {
                Toast.makeText(requireContext(), "Session created successfully!", Toast.LENGTH_SHORT).show()
                // Kembali ke halaman sebelumnya (SessionPlaceFragment)
                findNavController().navigate(R.id.action_addSessionFragment_to_sessionPlaceFragment)
            } else {
                Toast.makeText(requireContext(), "Failed to create session", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
