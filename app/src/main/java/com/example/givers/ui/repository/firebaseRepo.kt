package com.example.givers.ui.repository

import android.util.Log
import com.example.givers.ui.Manager.FirebaseManager
import com.example.givers.ui.Model.NeedyModel
import com.example.givers.ui.utils.DataResult
import kotlinx.coroutines.flow.Flow

class firebaseRepo( var firebaseManager: FirebaseManager) {

    suspend fun getAllDataNeedy():DataResult<Flow<MutableList<NeedyModel>>>{
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