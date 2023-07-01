package com.example.givers.app.Model

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class needyModel(var nationalID:String,var location:String,var phoneNumbr:String,var deviceId:String,var itemId:String = ""): Parcelable
