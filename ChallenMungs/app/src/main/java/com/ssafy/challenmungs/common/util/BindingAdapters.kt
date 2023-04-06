package com.ssafy.challenmungs.common.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.domain.entity.challenge.Participant
import com.ssafy.challenmungs.domain.entity.challenge.RankDetail
import com.ssafy.challenmungs.domain.entity.mypage.BalanceDetail
import com.ssafy.challenmungs.domain.entity.mypage.BalanceHistory
import com.ssafy.challenmungs.presentation.challenge.panel.ParticipantsListAdapter
import com.ssafy.challenmungs.presentation.challenge.panel.RankListAdapter
import com.ssafy.challenmungs.presentation.mypage.BankStatementInnerAdapter
import com.ssafy.challenmungs.presentation.mypage.BankStatementOuterAdapter

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
    @BindingAdapter("android:setImageViewUrl")
    fun ImageView.setImageViewUrl(imgUrl: String?) {
        Glide.with(this.context)
            .load(imgUrl)
            .placeholder(R.drawable.bg_rect_pink_swan_radius10_image_not_found)
            .error(R.drawable.bg_rect_pink_swan_radius10_image_not_found)
            .centerCrop()
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:setCampaignContentImg")
    fun ImageView.setCampaignContentImg(imgUrl: String?) {
        Glide.with(this.context)
            .load(imgUrl)
            .placeholder(R.drawable.bg_rect_campaign_content_image_cannot_load)
            .error(R.drawable.bg_rect_campaign_content_image_cannot_load)
            .centerCrop()
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:setBasicCampaignImageUrl")
    fun ImageView.setBasicCampaignImageUrl(imgUrl: String?) {
        Glide.with(this.context)
            .load(imgUrl)
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
    @BindingAdapter("app:rank", "app:isTeam")
    fun RecyclerView.setRankList(items: ArrayList<RankDetail>?, isTeamPlay: Boolean) {
        if (this.adapter == null) {
            val lm = LinearLayoutManager(this.context)
            lm.orientation = LinearLayoutManager.VERTICAL
            val adapter = RankListAdapter(isTeamPlay)
            this.layoutManager = lm
            this.adapter = adapter
        }
        val myAdapter = (this.adapter as RankListAdapter)
        myAdapter.submitList(items?.toMutableList())
    }

    @JvmStatic
    @BindingAdapter("app:items")
    fun RecyclerView.setItems(items: List<BalanceHistory>) {
        if (this.adapter == null) {
            val lm = LinearLayoutManager(this.context)
            lm.orientation = LinearLayoutManager.VERTICAL
            val adapter = BankStatementOuterAdapter()
            this.layoutManager = lm
            this.adapter = adapter
        }
        val myAdapter = (this.adapter as BankStatementOuterAdapter)
        myAdapter.submitListBalanceHistory(items)
    }

    @JvmStatic
    @BindingAdapter("app:item")
    fun RecyclerView.setItem(items: List<BalanceDetail>) {
        if (this.adapter == null) {
            val lm = LinearLayoutManager(this.context)
            lm.orientation = LinearLayoutManager.VERTICAL
            val adapter = BankStatementInnerAdapter()
            this.layoutManager = lm
            this.adapter = adapter
        }
        val myAdapter = (this.adapter as BankStatementInnerAdapter)
        myAdapter.submitDetailList(items)
    }
}