package com.example.givers.app.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.givers.R
import com.example.givers.databinding.FragmentNeedyBinding
import com.example.givers.app.Adapter.NeedyAdapter
import com.example.givers.app.Model.DonationModel
import com.example.givers.app.exts.observeEvent
import com.example.givers.app.utils.Status
import com.example.givers.app.viewmodel.NeedyViewModel

class Needy : Fragment() {
    private lateinit var binding: FragmentNeedyBinding
    private var needyAdapter = NeedyAdapter(mutableListOf()) {}
    private val viewModel by activityViewModels<NeedyViewModel>()
    companion object {
        const val TAG = "NeedyFragmentTag"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNeedyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApi()
        observeViewModel()
    }

   private fun callApi(){
        viewModel.getImageResult()
    }

   private fun observeViewModel(){
        viewModel.getImageResult.observeEvent(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                    Log.d(TAG, "observeViewModel UploadText: Load")
                }
                Status.LOADING_MORE -> {
                    Log.d(TAG, "observeViewModel UploadText: Load_more")
                }
                Status.SUCCESS -> {
                    result.data?.let { createAdapter(it) }
                }
                else -> {
                    Log.d(TAG, "observeViewModel UploadText: Fail ${result.exception?.message}")
                }
            }
        }
    }

    private fun createAdapter(donationModel: MutableList<DonationModel>) {
        Log.d(TAG, "createAdapter: ${donationModel.get(1).id}")
        needyAdapter= NeedyAdapter(donationModel){
            viewModel.navigateToDetail(it)
            findNavController().navigate(R.id.action_needy_to_needyDetails)
        }
        binding.rvNeedy.adapter = needyAdapter
    }


}