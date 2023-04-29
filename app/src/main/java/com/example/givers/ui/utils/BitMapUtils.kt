package com.example.givers.ui.utils


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.*

object BitmapUtils {



    fun compressAndSetImage(result: Uri,activity:Context): ByteArray {

        var imageStream: InputStream? = null
        try {
            imageStream = activity.contentResolver?.openInputStream(
                result
            )
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        var bmp = BitmapFactory.decodeStream(imageStream)

        var stream: ByteArrayOutputStream? = ByteArrayOutputStream()
        bmp = getResizedBitmap(bmp, 100)
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray: ByteArray = stream?.toByteArray()!!
        try {
            stream?.close()
            stream = null
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return byteArray
    }

    private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }
}