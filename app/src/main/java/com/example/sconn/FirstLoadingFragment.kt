package com.example.sconn

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController



/**
 * A simple [Fragment] subclass.
 * Use the [FirstLoadingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstLoadingFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Otomatis pindah ke Login setelah 2 detik
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_firstLoadingFragment_to_loginFragment)
        }, 2000)
    }
}