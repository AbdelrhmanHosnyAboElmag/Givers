package com.example.givers.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.givers.R
import com.example.givers.app.exts.observeEvent
import com.example.givers.app.viewmodel.NeedyViewModel
import com.example.givers.databinding.FragmentNeedyBinding
import com.example.givers.databinding.FragmentNeedyDetailsBinding


class NeedyDetails : Fragment() {
    private lateinit var binding: FragmentNeedyDetailsBinding
    private val viewModel by activityViewModels<NeedyViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNeedyDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModels()
    }
    private fun observeViewModels() {
        viewModel.navigateToDetail.observeEvent(viewLifecycleOwner){
            binding.btnSubmit.setOnClickListener {

            }
        }
    }

}