package com.example.givers.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.givers.R
import com.example.givers.databinding.FragmentHelperBinding
import com.example.givers.ui.exts.observeEvent
import com.example.givers.ui.utils.Status
import com.example.givers.ui.viewmodel.HelperViewModel
import com.firebase.ui.auth.AuthUI


class Helper : Fragment() {
    private lateinit var binding: FragmentHelperBinding
    private val viewModel by activityViewModels<HelperViewModel>()
    private val pic_id = 123
    private var imageUri: Uri? = null
    companion object {
        const val TAG = "HelperFragment"
    }

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
        observeViewModel()
        setupView()
    }

    private fun setupView(){
        binding.ivItem.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, pic_id)
        }

        binding.btnUpload.setOnClickListener {
            if(!binding.edItemDescription.text.toString().isNullOrEmpty()&&binding.ivItem.drawable!=null) {
                viewModel.uploadImage(imageUri)
                viewModel.uploadText(binding.edItemDescription.text.toString())
            }else{
                Toast.makeText(requireContext(), getString(R.string.fail_upload_data), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun observeViewModel() {
        viewModel.uploadImageResult.observeEvent(viewLifecycleOwner){result->
            when (result.status) {
                Status.LOADING -> {
                    Log.d(TAG, "observeViewModel UploadImage: Load")
                }
                Status.LOADING_MORE -> {
                    Log.d(TAG, "observeViewModel UploadImage: Load_more")
                }
                Status.SUCCESS->{
                    Log.d(TAG, "observeViewModel UploadImage: Success")
                    if(result.data == true){
                        Log.d(TAG, "observeViewModel UploadImage: Success upload")

                    }else{
                        Log.d(TAG, "observeViewModel UploadImage: Fail Upload${result.exception}")
                    }
                }
                else -> {
                    Log.d(TAG, "observeViewModel UploadImage: Fail ${result.exception?.message}")

                }
            }

        }
        viewModel.uploadTextResult.observeEvent(viewLifecycleOwner){result->
            when (result.status) {
                Status.LOADING -> {
                    Log.d(TAG, "observeViewModel UploadText: Load")
                }
                Status.LOADING_MORE -> {
                    Log.d(TAG, "observeViewModel UploadText: Load_more")
                }
                Status.SUCCESS->{
                    Log.d(TAG, "observeViewModel UploadText: Success")
                    if(result.data == true){
                        Log.d(TAG, "observeViewModel UploadText: Success upload")

                    }else{
                        Log.d(TAG, "observeViewModel UploadText: Fail Upload${result.exception}")
                    }
                }
                else -> {
                    Log.d(TAG, "observeViewModel UploadText: Fail ${result.exception?.message}")

                }
            }

        }
    }

        private fun observeAuthenticationState() {

        viewModel.authenticationState.observe(requireActivity(), Observer { authenticationState ->
            when (authenticationState) {
                HelperViewModel.AuthenticationState.AUTHENTICATED -> {
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
        Log.d(TAG, "onActivityResult: e3r23r32${data?.data}")

        if (requestCode == pic_id && data != null && data.data != null){
            imageUri = data.data
            binding.ivItem.setImageURI(imageUri);
        }
    }

}