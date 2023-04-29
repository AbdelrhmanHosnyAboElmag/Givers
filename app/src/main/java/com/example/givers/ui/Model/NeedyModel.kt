package com.example.givers.ui.Model

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class NeedyModel (var userData:String="",var imageData:String=""): Parcelable