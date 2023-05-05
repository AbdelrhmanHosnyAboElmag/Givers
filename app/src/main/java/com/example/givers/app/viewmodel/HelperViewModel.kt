package com.example.givers.app.viewmodel

import androidx.lifecycle.*
import com.example.givers.app.Manager.FireBaseManagerLiveData
import com.example.givers.app.Manager.FirebaseManager
import com.example.givers.app.Model.DonationModel
import com.example.givers.app.utils.DataResult
import com.example.givers.app.utils.Event
import com.example.givers.app.utils.Status
import kotlinx.coroutines.launch

class HelperViewModel : ViewModel() {

    /**
     * UPLOAD HELPER ITEM RESULT
     */
    private val _uploadImageToStorage = MutableLiveData<Event<DataResult<Any>>>()
    val uploadImageToStorage get() : LiveData<Event<DataResult<Any>>> = _uploadImageToStorage

    fun uploadImageToStorage(donationModel: DonationModel,imageUri: ByteArray?) {
        // Avoid requesting more data while previous request is in progress
        val status = uploadImageToStorage.value?.getForcedValue()?.status
        if (status == Status.LOADING || status == Status.LOADING_MORE) return
        // Fetch data
        viewModelScope.launch {

            _uploadImageToStorage.value = Event(DataResult.loading())
            _uploadImageToStorage.value = Event(
                    FirebaseManager().uploadImageToStorage(donationModel,imageUri)
            )
        }
    }


    /**
     * AUTHENTICATION STATE
     */
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = Transformations.map(FireBaseManagerLiveData()) { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }


}