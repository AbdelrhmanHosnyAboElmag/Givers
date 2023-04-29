package com.example.givers.ui.viewmodel

import androidx.lifecycle.*
import com.example.givers.ui.Manager.FireBaseManagerLiveData
import com.example.givers.ui.Manager.FirebaseManager
import com.example.givers.ui.utils.DataResult
import com.example.givers.ui.utils.Event
import com.example.givers.ui.utils.Status
import kotlinx.coroutines.launch

class HelperViewModel : ViewModel() {

    /**
     * UPLOAD HELPER ITEM RESULT
     */
    private val _uploadImageToStorage = MutableLiveData<Event<DataResult<Any>>>()
    val uploadImageToStorage get() : LiveData<Event<DataResult<Any>>> = _uploadImageToStorage

    fun uploadImageToStorage(imageUri: ByteArray?,text:String) {
        // Avoid requesting more data while previous request is in progress
        val status = uploadImageToStorage.value?.getForcedValue()?.status
        if (status == Status.LOADING || status == Status.LOADING_MORE) return
        // Fetch data
        viewModelScope.launch {

            _uploadImageToStorage.value = Event(DataResult.loading())
            _uploadImageToStorage.value = Event(
                    FirebaseManager().uploadImageToStorage(text,imageUri)
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