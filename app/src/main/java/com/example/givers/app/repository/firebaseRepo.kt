package com.example.givers.app.repository

import android.util.Log
import com.example.givers.app.Manager.FirebaseManager
import com.example.givers.app.Model.DonationModel
import com.example.givers.app.Model.needyModel
import com.example.givers.app.utils.DataResult
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

class firebaseRepo( var firebaseManager: FirebaseManager) {

    suspend fun getAllDataNeedy():DataResult<Flow<MutableList<DonationModel>>>{
        return try {
            Log.d("zxcfadsfae", "observeViewModel UploadText: try)}"
            )
            DataResult.success(firebaseManager.getAllDataNeedy())
        }catch (e:Throwable){
            Log.d("zxcfadsfae", "observeViewModel UploadText: catch")

            DataResult.error(e)
        }
    }


    suspend fun uploadNeedyRequest(needymodel: needyModel): DataResult<Any> {
        return try {
            Log.d("zxcfadsfae", "observeViewModel UploadText: try)}"
            )
            firebaseManager.uploadNeedyRequest(needymodel)
        }catch (e:Throwable){
            Log.d("zxcfadsfae", "observeViewModel UploadText: catch")

            DataResult.error(e)
        }
    }

    suspend fun checkIfExistsTaskSuspend(nationalID: String):DataResult<Flow<List<DocumentSnapshot>>>  {
        Log.d("TETS111", "onResgisterClick:1.3")
        return try {
            Log.d("zxcfadsfae", "observeViewModel UploadText: try)}"
            )
            DataResult.success(firebaseManager.checkIfExistsTaskSuspend(nationalID))
        }catch (e:Throwable){
            Log.d("zxcfadsfae", "observeViewModel UploadText: catch")

            DataResult.error(e)
        }
    }
}