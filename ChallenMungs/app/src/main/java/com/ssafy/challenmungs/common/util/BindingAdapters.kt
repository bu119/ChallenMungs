package com.ssafy.challenmungs.common.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.challenmungs.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:profileImgString")
    fun ImageView.setProfileImg(imgUri: String?) {
        Glide.with(this.context)
            .load(imgUri)
            .placeholder(R.drawable.ic_profile_default)
            .error(R.drawable.ic_profile_default)
            .circleCrop()
            .into(this)
    }
}