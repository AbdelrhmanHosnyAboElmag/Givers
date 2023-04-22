package com.example.givers.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.*
import com.example.givers.ui.Manager.FireBaseManagerLiveData
import com.example.givers.ui.Manager.FirebaseManager
import com.example.givers.ui.utils.DataResult
import com.example.givers.ui.utils.Event
import com.example.givers.ui.utils.Status
import kotlinx.coroutines.launch

class HelperViewModel : ViewModel() {

    /**
     * UPLOAD IMAGE RESULT
     */
    private val _uploadImageResult = MutableLiveData<Event<DataResult<Any>>>()
    val uploadImageResult get() : LiveData<Event<DataResult<Any>>> = _uploadImageResult

    fun uploadImage(imageUri: Uri?) {
        // Avoid requesting more data while previous request is in progress
        val status = uploadImageResult.value?.getForcedValue()?.status
        if (status == Status.LOADING || status == Status.LOADING_MORE) return
        // Fetch data
        viewModelScope.launch {

            _uploadImageResult.value = Event(DataResult.loading())
            _uploadImageResult.value = Event(
                DataResult.success(
                    FirebaseManager().uploadImage(imageUri ?: Uri.EMPTY)
                )
            )
        }
    }

    /**
     * UPLOAD TEXT RESULT
     */
    private val _uploadTextResult = MutableLiveData<Event<DataResult<Any>>>()
    val uploadTextResult get() : LiveData<Event<DataResult<Any>>> = _uploadTextResult

    fun uploadText(text: String) {
        // Avoid requesting more data while previous request is in progress
        val status = uploadTextResult.value?.getForcedValue()?.status
        if (status == Status.LOADING || status == Status.LOADING_MORE) return
        // Fetch data
        viewModelScope.launch {

            _uploadTextResult.value = Event(DataResult.loading())
            _uploadTextResult.value = Event(
                DataResult.success(
                    FirebaseManager().uploadText(text)
                )
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