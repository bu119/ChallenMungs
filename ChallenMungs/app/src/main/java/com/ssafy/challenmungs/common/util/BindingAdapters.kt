package com.ssafy.challenmungs.common.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.domain.entity.challenge.Participant
import com.ssafy.challenmungs.domain.entity.challenge.RankDetail
import com.ssafy.challenmungs.presentation.challenge.panel.ParticipantsListAdapter
import com.ssafy.challenmungs.presentation.challenge.panel.RankListAdapter

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
    @BindingAdapter("android:campaignBannerUri")
    fun ImageView.setCampaignBannerImage(imgUri: String?) {
        Glide.with(this.context)
            .load(imgUri)
            .placeholder(R.drawable.bg_rect_pink_swan_radius10_image_not_found)
            .error(R.drawable.bg_rect_pink_swan_radius10_image_not_found)
            .centerCrop()
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:campaignContentUri")
    fun ImageView.setCampaignContentImage(imgUri: String?) {
        Glide.with(this.context)
            .load(imgUri)
            .placeholder(R.drawable.bg_rect_pink_swan_image_not_found)
            .error(R.drawable.bg_rect_pink_swan_image_not_found)
            .centerCrop()
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("app:imgRes")
    fun ImageView.setImgResource(resId: Int) {
        this.setImageResource(resId)
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

    @JvmStatic
    @BindingAdapter("app:rank")
    fun RecyclerView.setRankList(items: ArrayList<RankDetail>?) {
        if (this.adapter == null) {
            val lm = LinearLayoutManager(this.context)
            lm.orientation = LinearLayoutManager.VERTICAL
            val adapter = RankListAdapter()
            this.layoutManager = lm
            this.adapter = adapter
        }
        val myAdapter = (this.adapter as RankListAdapter)
        myAdapter.submitList(items?.toMutableList())
    }
}