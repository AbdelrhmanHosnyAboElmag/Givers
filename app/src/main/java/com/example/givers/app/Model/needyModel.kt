package com.example.givers.app.Model

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class needyModel(var name:String,var location:String,var phoneNumbr:String): Parcelable
