package com.example.givers.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.givers.R

class GettingStartedFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_getting_started, container, false)
    }

    override fun onResume() {
        super.onResume()
        ResgisterOnclick()
    }

    private fun ResgisterOnclick(){
        findNavController().navigate(R.id.action_gettingStartedFragment_to_option)

    }
}