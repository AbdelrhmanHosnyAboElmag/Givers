package com.example.givers.ui.viewmodel

import android.content.Context
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.givers.ui.source.FireBaseManagerLiveData

class LoginViewModel  : ViewModel() {


    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = Transformations.map(FireBaseManagerLiveData()) { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }


}