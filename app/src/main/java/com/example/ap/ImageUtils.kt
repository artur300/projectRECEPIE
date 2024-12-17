package com.example.ap.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

object ImageUtils {
    fun processImage(context: Context, imageUri: Uri?, maxWidth: Int, maxHeight: Int): Bitmap? {
        return try {
            val inputStream = imageUri?.let { context.contentResolver.openInputStream(it) }
            inputStream?.use { stream ->
                val options = BitmapFactory.Options().apply {
                    inJustDecodeBounds = true // שלב בדיקה בלבד
                    BitmapFactory.decodeStream(stream, null, this)

                    // חישוב יחס ההקטנה
                    inSampleSize = calculateInSampleSize(this, maxWidth, maxHeight)
                    inJustDecodeBounds = false
                }

                val finalStream = context.contentResolver.openInputStream(imageUri)
                BitmapFactory.decodeStream(finalStream, null, options)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}
