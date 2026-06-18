package com.example.sconn

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sconn.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Bottom Navigation dengan NavController
        val navController = findNavController()
        binding.bottomNav.setupWithNavController(navController)

        // Ambil Nama User dari SharedPreferences
        val sharedPref = requireContext().getSharedPreferences("SconnPrefs", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("USER_NAME", "User")
        binding.tvHello.text = "Hello, $userName!"

        // Navigation manual jika diperlukan (opsional, karena setupWithNavController sudah menangani menu)
        binding.cardBrowsePlaces.setOnClickListener {
            navController.navigate(R.id.sessionPlaceFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
