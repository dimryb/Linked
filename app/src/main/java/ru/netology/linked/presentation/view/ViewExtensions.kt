package ru.netology.linked.presentation.view

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter

fun ImageView.load(url: String, vararg transforms: BitmapTransformation = emptyArray()) =
    Glide.with(this)
        .load(url)
        .timeout(10_000)
        .transform(*transforms)
        .into(this)

fun ImageView.loadCircleCrop(url: String, vararg transforms: BitmapTransformation = emptyArray()) =
    load(url, CircleCrop(), *transforms)

fun ImageView.loadAuthorAvatar(fileName: String) = loadCircleCrop(fileName)
fun ImageView.loadImageMedia(fileName: String) {
    load(fileName, FitCenter())
}