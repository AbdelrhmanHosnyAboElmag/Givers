package com.example.givers.ui.exts

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.givers.ui.utils.Event

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner) {
        it.getValue()?.let(observer)
    }
}