package com.ssafy.challenmungs.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemRecentlyAddedCampaignCardBinding

class RecentlyAddedCampaignAdpater(private val arrayList: List<Map<String, Any>>) :
    RecyclerView.Adapter<RecentlyAddedCampaignViewHolder>() {

    private lateinit var binding: ItemRecentlyAddedCampaignCardBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentlyAddedCampaignViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recently_added_campaign_card,
            parent,
            false
        )
        return RecentlyAddedCampaignViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentlyAddedCampaignViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int = arrayList.size
}

class RecentlyAddedCampaignViewHolder(private val binding: ItemRecentlyAddedCampaignCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(dto: Map<String, Any>) {
        Glide.with(binding.ivBanner.context)
            .load(dto.get("thumbnail"))
            .into(binding.ivBanner)
        binding.progressBar.progress =
            ((dto.get("collectAmount").toString().toDouble() / dto.get("targetAmount").toString()
                .toDouble()) * 100).toInt()
        binding.tvTitle.text = dto.get("title").toString()
        binding.tvDonationCharity.text = dto.get("name").toString()
        binding.tvCheerCount.text = dto.get("loveCount").toString()
        binding.tvProgress.text = "${
            ((dto.get("collectAmount").toString().toDouble() / dto.get("targetAmount").toString()
                .toDouble()) * 100).toInt()
        }%"
        binding.tvCampaignAmount.text =
            "${dto.get("collectAmount")} / ${dto.get("targetAmount")} KLAY"

        initListener()
    }

    private fun initListener() {

    }
}