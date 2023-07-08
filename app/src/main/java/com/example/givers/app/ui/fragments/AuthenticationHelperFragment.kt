package com.example.givers.app.ui.fragments

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.givers.R
import com.example.givers.databinding.FragmentAuthenticationHelperBinding
import com.example.givers.app.viewmodel.HelperViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class AuthenticationHelperFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationHelperBinding
    private val viewModel by activityViewModels<HelperViewModel>()

    companion object {
        const val TAG = "AuthenticationHelper"
        const val SIGN_IN_RESULT_CODE = 1001
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthenticationHelperBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return binding.root
    }
    private fun observeAuthenticationState() {

        viewModel.authenticationState.observe(requireActivity(), Observer { authenticationState ->
            when (authenticationState) {
                HelperViewModel.AuthenticationState.AUTHENTICATED -> {
                    binding.btSign.visibility=View.GONE
                    goToHelper()
                }
                else -> {
                    binding.btSign.visibility=View.VISIBLE

                }
            }
        })
    }

    private fun goToHelper() {
        lifecycleScope.launchWhenResumed {
            findNavController().navigate(R.id.action_authenticationHelper_to_helper)
        }

    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_Givers)
            .build()

        val pendingIntent = PendingIntent.getActivity(
            requireContext(), SIGN_IN_RESULT_CODE, signInIntent, PendingIntent.FLAG_IMMUTABLE
        )

        startActivityForResult(signInIntent, SIGN_IN_RESULT_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in user.
                //goToHelper()
                Log.i(
                    TAG,
                    "Successfully signed in user " +
                            "${FirebaseAuth.getInstance().currentUser?.displayName}!"
                )
            } else {
                // Sign in failed. If response is null the user canceled the sign-in flow using
                // the back button. Otherwise check response.getError().getErrorCode() and handle
                // the error.
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }


    override fun onStart() {
        super.onStart()
        binding.btSign.setOnClickListener {
            launchSignInFlow()
        }
        observeAuthenticationState()

    }

}