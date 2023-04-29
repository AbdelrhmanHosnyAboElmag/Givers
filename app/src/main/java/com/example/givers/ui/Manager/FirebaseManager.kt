package com.example.givers.ui.Manager

import android.util.Log
import com.example.givers.ui.Model.NeedyModel
import com.example.givers.ui.utils.Constants
import com.example.givers.ui.utils.DataResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
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

    suspend fun uploadImageToStorage(text: String, imageUri: ByteArray?): DataResult<Any> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Log.d("HelperFragmentTag", "uploadImageLink:${imageUri} ")
                DataResult.success(
                    imageUri?.let {
                        storageRefernce.putBytes(it)
                            .addOnCompleteListener {
                                storageRefernce.downloadUrl.addOnSuccessListener {
                                    Log.d("HelperFragmentTag", "getDownloadLink:${it} ")
                                    GlobalScope.launch {
                                        uploadHelperItem(text, it.toString())
                                    }
                                }
                            }.await()
                    }
                )
            } catch (e: Throwable) {
                DataResult.error(e)
            }
        }

    suspend fun uploadHelperItem(text: String, imageLink: String): DataResult<Any> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                DataResult.success(
                    db.collection(Constants.textUser).add(
                        hashMapOf(
                            "userData" to text,
                            "imageData" to imageLink
                        )
                    ).await()
                )
            } catch (e: Throwable) {
                DataResult.error(e)
            }
        }

    suspend fun getAllDataNeedy() = callbackFlow<MutableList<NeedyModel>> {
            var lister =Firebase.firestore.collection(Constants.textUser).get().addOnSuccessListener {
                trySend(it.toObjects(NeedyModel::class.java))
            }
            awaitClose { lister}
    }

}