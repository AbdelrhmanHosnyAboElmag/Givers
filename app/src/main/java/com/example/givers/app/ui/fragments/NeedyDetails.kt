package com.example.givers.app.ui.fragments

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.givers.app.Model.DonationModel
import com.example.givers.app.Model.needyModel
import com.example.givers.app.exts.observeEvent
import com.example.givers.app.utils.Constants
import com.example.givers.app.utils.Status
import com.example.givers.app.viewmodel.NeedyViewModel
import com.example.givers.databinding.FragmentNeedyDetailsBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*


class NeedyDetails : Fragment() {
    private lateinit var binding: FragmentNeedyDetailsBinding
    private val viewModel by activityViewModels<NeedyViewModel>()
    private lateinit var donationItem: DonationModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNeedyDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModels()
        onResgisterClick()
    }

    private fun observeViewModels() {
        viewModel.navigateToDetail.observeEvent(viewLifecycleOwner) { result ->
            donationItem = result
        }
        viewModel.getNeedyRequestResult.observeEvent(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                    Log.d(Needy.TAG, "observeViewModel UploadText: Load")
                }
                Status.LOADING_MORE -> {
                    Log.d(Needy.TAG, "observeViewModel UploadText: Load_more")
                }
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.d(
                        Needy.TAG,
                        "observeViewModel UploadText: Fail ${result.exception?.message}"
                    )
                }
            }
        }
        viewModel.checkIfExistsTaskSuspend.observeEvent(viewLifecycleOwner) { result ->
            Log.d("TETS111", "onResgisterClick:2 ")
            when (result.status) {
                Status.LOADING -> {
                    Log.d("TETS111", "onResgisterClick:3")
                    Log.d(Needy.TAG, "observeViewModel UploadText: Load")
                }
                Status.LOADING_MORE -> {
                    Log.d(Needy.TAG, "observeViewModel UploadText: Load_more")
                }
                Status.SUCCESS -> {
                    Log.d("ascbh", "observeViewModels:${result.data}")
                    Log.d("TETS111", "onResgisterClick:4")

                    if (result.data?.size == 0) {
                        Log.d("TETS111", "onResgisterClick:5 ")
                        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                        viewModel.getNeedyRequestResult(
                            needyModel(
                                binding.txtLanguage.text.toString(),
                                binding.txtGroupFortyFive.text.toString(),
                                binding.txtLanguageOne.text.toString(),
                                deviceID(),
                                donationItem.id
                            )
                        )
                    } else {
                        Log.d("TETS111", "onResgisterClick:6")
                        //found before
                        Toast.makeText(requireContext(), "already you has request", Toast.LENGTH_SHORT).show()
                    }

                }
                else -> {
                    Log.d("TETS111", "onResgisterClick:7")
                    Log.d(
                        Needy.TAG,
                        "observeViewModel UploadText: Fail ${result.exception?.message}"
                    )
                }
            }
        }
    }

    private fun onResgisterClick() {
        binding.btnSubmit.setOnClickListener {
            Log.d("TETS111", "onResgisterClick:1 ")
            viewModel.checkIfExistsTaskSuspend(binding.txtLanguage.text.toString())
        }
    }

    private fun deviceID(): String {
        return Settings.Secure.getString(
            requireContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

}