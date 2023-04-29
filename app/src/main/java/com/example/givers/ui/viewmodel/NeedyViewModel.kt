package com.example.givers.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givers.ui.Manager.FirebaseManager
import com.example.givers.ui.Model.NeedyModel
import com.example.givers.ui.repository.firebaseRepo
import com.example.givers.ui.utils.DataResult
import com.example.givers.ui.utils.Event
import com.example.givers.ui.utils.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NeedyViewModel:ViewModel() {
    /**
     * GET HELPER ITEM RESULT
     */
    private val _getImageResult = MutableLiveData<Event<DataResult<MutableList<NeedyModel>>>>()
    val getImageResult get() : LiveData<Event<DataResult<MutableList<NeedyModel>>>> = _getImageResult

    fun getImageResult() {
        // Avoid requesting more data while previous request is in progress
        val status = getImageResult.value?.getForcedValue()?.status
        if (status == Status.LOADING || status == Status.LOADING_MORE) return
        // Fetch data
        viewModelScope.launch {

            _getImageResult.value = Event(DataResult.loading())
            firebaseRepo(FirebaseManager()).getAllDataNeedy().data?.collect{
                _getImageResult.value = Event(
                    DataResult.success(it)
                )
            }

        }
    }
}