package com.example.givers.app.viewmodel

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givers.app.Manager.FirebaseManager
import com.example.givers.app.Model.DonationModel
import com.example.givers.app.Model.needyModel
import com.example.givers.app.repository.firebaseRepo
import com.example.givers.app.utils.DataResult
import com.example.givers.app.utils.Event
import com.example.givers.app.utils.Status
import com.google.firebase.firestore.DocumentSnapshot
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

    /**
     * UPLOAD NEEDY REQUEST
     */
    private val _getNeedyRequestResult = MutableLiveData<Event<DataResult<MutableList<DonationModel>>>>()
    val getNeedyRequestResult get() : LiveData<Event<DataResult<MutableList<DonationModel>>>> = _getNeedyRequestResult

    fun getNeedyRequestResult(needyModel: needyModel) {
        // Avoid requesting more data while previous request is in progress
        val status = getNeedyRequestResult.value?.getForcedValue()?.status
        if (status == Status.LOADING || status == Status.LOADING_MORE) return
        // Fetch data
        viewModelScope.launch {
            _getNeedyRequestResult.value = Event(DataResult.loading())
            firebaseRepo(FirebaseManager()).uploadNeedyRequest(needyModel)

        }
    }



    /**
     * UPLOAD NEEDY REQUEST
     */
    private val _checkIfExistsTaskSuspend = MutableLiveData<Event<DataResult<List<DocumentSnapshot>>>>()
    val checkIfExistsTaskSuspend get() : LiveData<Event<DataResult<List<DocumentSnapshot>>>> = _checkIfExistsTaskSuspend

    fun checkIfExistsTaskSuspend(deviceId: String) {
        Log.d("TETS111", "onResgisterClick:1.1")
        // Avoid requesting more data while previous request is in progress
        val status = checkIfExistsTaskSuspend.value?.getForcedValue()?.status
        if (status == Status.LOADING || status == Status.LOADING_MORE) return
        // Fetch data
        viewModelScope.launch {
            _checkIfExistsTaskSuspend.value = Event(DataResult.loading())
            firebaseRepo(FirebaseManager()).checkIfExistsTaskSuspend(deviceId).data?.collect{
                _checkIfExistsTaskSuspend.value = Event(
                    DataResult.success(it)
                )
            }

        }
    }


}