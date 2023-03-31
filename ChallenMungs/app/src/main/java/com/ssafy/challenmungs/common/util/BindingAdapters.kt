package com.ssafy.challenmungs.common.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.domain.entity.challenge.Participant
import com.ssafy.challenmungs.presentation.challenge.ParticipantsListAdapter

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

    @JvmStatic
    @BindingAdapter("app:participant")
    fun RecyclerView.setParticipantList(items: ArrayList<Participant>?) {
        if (this.adapter == null) {
            val lm = LinearLayoutManager(this.context)
            lm.orientation = LinearLayoutManager.HORIZONTAL
            val adapter = ParticipantsListAdapter()
            this.layoutManager = lm
            this.adapter = adapter
        }
        val myAdapter = (this.adapter as ParticipantsListAdapter)
        myAdapter.submitList(items?.toMutableList())
    }
}