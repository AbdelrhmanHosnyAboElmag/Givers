package com.example.givers.ui.source

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FireBaseManagerLiveData : LiveData<FirebaseUser?>() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        value = firebaseAuth.currentUser
    }
    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }
    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}