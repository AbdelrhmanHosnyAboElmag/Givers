package com.example.givers.app.Manager

import android.content.Context
import android.provider.Settings
import android.util.Log
import com.example.givers.app.Model.DonationModel
import com.example.givers.app.Model.needyModel
import com.example.givers.app.utils.Constants
import com.example.givers.app.utils.DataResult
import com.example.givers.app.viewmodel.NeedyViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.security.AccessController.getContext
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


    suspend fun uploadImageToStorage(
        donationModel: DonationModel,
        imageUri: ByteArray?
    ): DataResult<Any> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                DataResult.success(
                    imageUri?.let {
                        storageRefernce.putBytes(it)
                            .addOnCompleteListener {
                                storageRefernce.downloadUrl.addOnSuccessListener {
                                    Log.d("HelperFragmentTag", "getDownloadLink:${it} ")
                                    GlobalScope.launch {
                                        donationModel.itemImage = it.toString()
                                        uploadHelperItem(donationModel)
                                    }
                                }
                            }.await()
                    }
                )
            } catch (e: Throwable) {
                DataResult.error(e)
            }
        }

    suspend fun uploadHelperItem(donationModel: DonationModel): DataResult<Any> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                DataResult.success(
                    db.collection(Constants.donationitems).add(
                        hashMapOf(
                            "itemDescription" to donationModel.itemDescription,
                            "itemImage" to donationModel.itemImage,
                            "itemType" to donationModel.itemType,
                            "id" to donationModel.id,
                            "isTake" to donationModel.isTake
                        )
                    ).await()
                )
            } catch (e: Throwable) {
                DataResult.error(e)
            }
        }

    suspend fun uploadNeedyRequest(needymodel: needyModel): DataResult<Any> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                DataResult.success(
                    db.collection(Constants.needyPeople).add(
                        hashMapOf(
                            "phoneNumber" to needymodel.phoneNumbr,
                            "location" to needymodel.location,
                            "national_id" to needymodel.nationalID,
                            "device_id" to needymodel.deviceId,
                            "item_id" to needymodel.itemId
                        )
                    ).await()
                )
            } catch (e: Throwable) {
                DataResult.error(e)
            }
        }


    suspend fun checkIfExistsTaskSuspend(queryTask: Task<QuerySnapshot>): DataResult<List<DocumentSnapshot>> {
        return suspendCancellableCoroutine { continuation ->
            queryTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    if (!querySnapshot.isEmpty) {
                        continuation.resume(DataResult.success(querySnapshot.documents), {})
                    } else {

                        continuation.resume(DataResult.success(Collections.emptyList()), {})
                    }
                } else {
                    continuation.resume(DataResult.error(task.exception), {})
                }
            }
//            continuation.invokeOnCancellation {
//
//            }
        }
    }

    suspend fun getAllDataNeedy() = callbackFlow<MutableList<DonationModel>> {
        var lister =
            Firebase.firestore.collection(Constants.donationitems).get().addOnSuccessListener {
                trySend(it.toObjects(DonationModel::class.java))
            }
        awaitClose { lister }
    }

    suspend fun checkIfExistsTaskSuspend(nationalID: String) =
        callbackFlow<List<DocumentSnapshot>> {

            val query = FirebaseFirestore.getInstance().collection(Constants.needyPeople)
                .whereEqualTo("national_id", nationalID)
            val queryTask = query.get()
            val lister = queryTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    if (!querySnapshot.isEmpty) {
                        Log.d("TEST111", "checkIfExistsTaskSuspend:1")
                        trySend(querySnapshot.documents)
                    } else {
                        Log.d("TEST111", "checkIfExistsTaskSuspend:2")
                        trySend(Collections.emptyList())
                    }
                }
            }
            awaitClose { lister }
        }
}


