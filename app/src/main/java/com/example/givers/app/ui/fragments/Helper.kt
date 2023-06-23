package com.example.givers.app.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.example.givers.app.Model.DonationModel
import com.example.givers.databinding.FragmentHelperBinding
import com.example.givers.app.exts.observeEvent
import com.example.givers.app.utils.BitmapUtils
import com.example.givers.app.utils.Status
import com.example.givers.app.viewmodel.HelperViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.runBlocking
import java.util.*


class Helper : Fragment() {
    private lateinit var binding: FragmentHelperBinding
    private val viewModel by activityViewModels<HelperViewModel>()
    private val pic_id = 123
    private var imageUri: Uri? = null

    companion object {
        const val TAG = "HelperFragmentTag"
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

    private fun setupView() {
        binding.ivItem.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, pic_id)
        }

        binding.btnUpload.setOnClickListener {
            if (!binding.edItemDescription.text.toString()
                    .isNullOrEmpty() && binding.ivItem.drawable != null
            ) {
                startLoadView()
                runBlocking {
                    viewModel.uploadImageToStorage(
                        donationModel = DonationModel(
                            binding.edItemDescription.text.toString(),
                            imageUri?.let { it1 ->
                                BitmapUtils.compressAndSetImage(
                                    it1,
                                    requireActivity()
                                )
                            }.toString(),"TEST TYPE"
                        ),       imageUri?.let { it1 ->
                            BitmapUtils.compressAndSetImage(
                                it1,
                                requireActivity()
                            )
                        }

                    )
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.fail_upload_data),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observeViewModel() {

        viewModel.uploadImageToStorage.observeEvent(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                    Log.d(TAG, "observeViewModel UploadText: Load")
                }
                Status.LOADING_MORE -> {
                    Log.d(TAG, "observeViewModel UploadText: Load_more")
                }
                Status.SUCCESS -> {
                    stopLoadView()
                    Log.d(TAG, "observeViewModel UploadText: Success upload")
                }
                else -> {
                    stopLoadView()
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

    private fun startLoadView() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun stopLoadView() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: e3r23r32${data?.data}")

        if (requestCode == pic_id && data != null && data.data != null) {
            imageUri = data.data
            binding.ivItem.setImageURI(imageUri)

        }
    }

}