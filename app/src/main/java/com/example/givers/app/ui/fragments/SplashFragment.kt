package com.example.givers.app.ui.fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.givers.R
import com.example.givers.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationStart()
    }

    fun animationStart(){
        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                // Animation start callback
            }

            override fun onAnimationEnd(animation: Animation) {
                findNavController().navigate(R.id.action_splash_to_gettingStartedFragment)

            }

            override fun onAnimationRepeat(animation: Animation) {
                // Animation repeat callback
            }
        })
        binding.animationView.startAnimation(fadeInAnimation)
        binding.txtAppName.startAnimation(fadeInAnimation)

    }

}