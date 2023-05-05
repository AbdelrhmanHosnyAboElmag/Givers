package com.example.givers.app.repository

import android.util.Log
import com.example.givers.app.Manager.FirebaseManager
import com.example.givers.app.Model.DonationModel
import com.example.givers.app.utils.DataResult
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
}