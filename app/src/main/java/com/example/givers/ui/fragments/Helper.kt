package com.example.givers.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.givers.databinding.FragmentHelperBinding
import com.example.givers.ui.viewmodel.LoginViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.R


class Helper : Fragment() {
    private lateinit var binding: FragmentHelperBinding
    private val viewModel by activityViewModels<LoginViewModel>()
    private val pic_id = 123


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelperBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        observeAuthenticationState()
        binding.ivItem.setOnClickListener {
            val camera_intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera_intent, pic_id)
        }
    }

    private fun observeAuthenticationState() {

        viewModel.authenticationState.observe(requireActivity(), Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    binding.btnLogout.setOnClickListener {
                        AuthUI.getInstance().signOut(requireContext())
                    }
                }
                else -> {
                    lifecycleScope.launchWhenResumed {

                        findNavController().navigate(com.example.givers.R.id.action_global_authenticationHelper)
                    }
                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === pic_id) {
            val photo: Bitmap? = data!!.extras!!["data"] as Bitmap?
            binding.ivItem.setImageBitmap(photo)
        }
    }

}