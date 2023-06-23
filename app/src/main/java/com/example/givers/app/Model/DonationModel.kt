package com.example.givers.app.Model

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import kotlinx.android.parcel.Parcelize
import java.util.*

@Keep
@Parcelize
data class DonationModel (var itemDescription:String="", var itemImage:String="",var itemType:String="",var id:String = UUID.randomUUID().toString(),var isTake:Boolean =false): Parcelable