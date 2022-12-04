package com.zii.core.utils

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.zii.core.R

//fun ImageView.loadImage(
//    uri: String?,
//    cornerRadius: Int = 1,
//    progressColor: Int = R.color.default_progress_color
//) {
//    val colorCompat = ContextCompat.getColor(this.context, progressColor)
//    Glide.with(this)
//        .load(uri.toString())
//        .placeholder(getProgressDrawable(this.context, colorCompat))
//        .error(R.drawable.ic_default_image)
//        .transform(RoundedCorners(cornerRadius))
//        .transition(DrawableTransitionOptions.withCrossFade(300))
//        .into(this)
//}
//
//fun ImageView.loadImageCircleCrop(
//    uri: String?,
//    progressColor: Int = R.color.default_progress_color
//) {
//    val colorCompat = ContextCompat.getColor(this.context, progressColor)
//    Glide.with(this)
//        .load(uri.toString())
//        .placeholder(getProgressDrawable(this.context, colorCompat))
//        .error(R.drawable.ic_person)
//        .circleCrop()
//        .transition(DrawableTransitionOptions.withCrossFade(300))
//        .into(this)
//}
//
//fun getProgressDrawable(
//    context: Context,
//    color: Int = R.color.default_progress_color
//): CircularProgressDrawable {
//    return CircularProgressDrawable(context).apply {
//        strokeWidth = 10f
//        centerRadius = 50f
//        setColorSchemeColors(color)
//        start()
//    }
//}