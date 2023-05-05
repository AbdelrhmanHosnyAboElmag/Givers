package com.example.givers.app.viewmodel

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givers.app.Manager.FirebaseManager
import com.example.givers.app.Model.DonationModel
import com.example.givers.app.repository.firebaseRepo
import com.example.givers.app.utils.DataResult
import com.example.givers.app.utils.Event
import com.example.givers.app.utils.Status
import kotlinx.coroutines.launch

class NeedyViewModel:ViewModel() {
    /**
     * GET HELPER ITEM RESULT
     */
    private val _getImageResult = MutableLiveData<Event<DataResult<MutableList<DonationModel>>>>()
    val getImageResult get() : LiveData<Event<DataResult<MutableList<DonationModel>>>> = _getImageResult

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
    /**
     * Is navigate to needyDetail
     */
    private val _navigateToDetail = MutableLiveData<Event<DonationModel>>()
    val navigateToDetail get() : LiveData<Event<DonationModel>> = _navigateToDetail
    fun navigateToDetail(needy: DonationModel) {
        viewModelScope.launch {
            _navigateToDetail.value = Event(needy)
        }
    }



}