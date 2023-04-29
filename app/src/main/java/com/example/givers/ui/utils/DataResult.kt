package com.example.givers.ui.utils

import com.example.givers.ui.Model.NeedyModel
import kotlinx.coroutines.flow.Flow

data class DataResult<out T>(val status: Status, val data: T?, val exception: Throwable?) {
    companion object {
        fun <T> success(data: T? = null): DataResult<T> {
            return DataResult(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Throwable? = null): DataResult<T> {
            return DataResult(Status.ERROR, null, exception)
        }

        fun <T> loading(): DataResult<T> {
            return DataResult(Status.LOADING, null, null)
        }
    }
}