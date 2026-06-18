package com.example.sconn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sconn.databaseLite.DatabaseHelper
import com.example.sconn.databinding.FragmentSessionPlaceBinding

class SessionPlaceFragment : Fragment() {
    private var _binding: FragmentSessionPlaceBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sessionAdapter: SessionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionPlaceBinding.inflate(inflater, container, false)
        dbHelper = DatabaseHelper.getInstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadSessions()

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_sessionPlaceFragment_to_addSessionFragment)
        }
    }

    private fun setupRecyclerView() {
        sessionAdapter = SessionAdapter(emptyList())
        binding.rvSessions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sessionAdapter
        }
    }

    private fun loadSessions() {
        val sessions = dbHelper.getAllSessions()
        sessionAdapter.updateData(sessions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
