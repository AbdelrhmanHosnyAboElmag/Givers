package com.example.givers.ui.Manager

import android.net.Uri
import android.util.Log
import com.example.givers.ui.utils.Constants
import com.example.givers.ui.utils.DataResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class FirebaseManager {
    private val FirebaseStorage = Firebase.storage
    private val db = Firebase.firestore
    private val storageRefernce = FirebaseStorage.getReference(folderPath())
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private fun folderPath(): String {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH)
        val now = Date()
        val fileName: String = formatter.format(now)
        return "images/$fileName"
    }

    suspend fun uploadImage(imageUri: Uri): Any? = withContext(Dispatchers.IO) {
        return@withContext try {
            storageRefernce.putFile(imageUri).addOnSuccessListener {
                DataResult.success(true)
            }.addOnFailureListener {
                it.cause
            }.await()
        } catch (e: Exception) {
            DataResult.error<java.lang.Exception>(e)
        }
    }

    suspend fun uploadText(text: String): Any? = withContext(Dispatchers.IO) {
        return@withContext try {
            db.collection(Constants.textUser).document(userId!!).set(hashMapOf("userData" to text)).addOnSuccessListener {
                DataResult.success(true)
            }.addOnFailureListener {
                it.cause
            }.await()
        } catch (e: Exception) {
            DataResult.error<java.lang.Exception>(e)
        }
    }
}