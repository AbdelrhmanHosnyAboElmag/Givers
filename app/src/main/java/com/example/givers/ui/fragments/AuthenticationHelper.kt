package com.example.givers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.givers.R
import com.example.givers.databinding.FragmentAuthenticationHelperBinding
import com.example.givers.databinding.FragmentSplashBinding

class AuthenticationHelper : Fragment() {

    private lateinit var binding: FragmentAuthenticationHelperBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthenticationHelperBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.button.setOnClickListener { findNavController().navigate(R.id.action_authenticationHelper_to_helper)}
    }

}