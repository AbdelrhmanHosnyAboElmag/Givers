package com.example.givers.ui.fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.givers.R
import com.example.givers.databinding.ActivityMainBinding
import com.example.givers.databinding.FragmentSplashBinding


class Splash : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)
        binding.animationView.addAnimatorListener(object :AnimatorListener{
            override fun onAnimationStart(p0: Animator) {

            }

            override fun onAnimationEnd(p0: Animator) {
                findNavController().navigate(R.id.action_splash_to_option )

            }

            override fun onAnimationCancel(p0: Animator) {

            }

            override fun onAnimationRepeat(p0: Animator) {
            }

        })
        return binding.root
    }

}