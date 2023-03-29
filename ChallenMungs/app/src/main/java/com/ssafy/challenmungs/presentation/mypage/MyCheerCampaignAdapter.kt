package com.ssafy.challenmungs.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemCampaignCardBinding
import com.ssafy.challenmungs.domain.entity.campaign.CampaignCard

class MyCheerCampaignAdapter(var items: List<CampaignCard> = listOf()) :
    RecyclerView.Adapter<MyCheerCampaignAdapter.CampaignViewHolder>() {

    lateinit var binding: ItemCampaignCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_campaign_card, parent, false
        )
        return CampaignViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class CampaignViewHolder(private val binding: ItemCampaignCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: CampaignCard) {
            binding.campaign = data
        }
    }
}