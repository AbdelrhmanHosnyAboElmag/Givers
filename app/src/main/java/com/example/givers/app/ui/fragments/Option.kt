package com.example.givers.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.givers.R
import com.example.givers.databinding.FragmentOptionBinding


class Option : Fragment() {

    private lateinit var binding: FragmentOptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOptionBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnNeedy.setOnClickListener {
            findNavController().navigate(R.id.action_option_to_needy)
        }
        binding.btnDonor.setOnClickListener {
            findNavController().navigate(R.id.action_option_to_authenticationHelper)
        }
    }

}