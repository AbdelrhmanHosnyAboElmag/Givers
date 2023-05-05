package com.example.givers.app.Model

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class DonationModel (var itemDescription:String="", var itemImage:String="",var itemType:String="",var id:String =""): Parcelable