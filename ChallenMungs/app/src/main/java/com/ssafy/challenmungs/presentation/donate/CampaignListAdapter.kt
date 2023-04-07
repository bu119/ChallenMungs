package com.ssafy.challenmungs.presentation.donate

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemCampaignCardBinding
import com.ssafy.challenmungs.domain.entity.campaign.CampaignCard

class CampaignListAdapter(private val donateViewModel: DonateViewModel) :
    RecyclerView.Adapter<CampaignListAdapter.CampaignListViewHolder>() {

    lateinit var campaignList: List<CampaignCard>
    lateinit var binding: ItemCampaignCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignListViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_campaign_card,
            parent,
            false
        )
        return CampaignListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CampaignListViewHolder, position: Int) {
        holder.bind(campaignList[position])
    }

    override fun getItemCount(): Int = campaignList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(arrayList: List<CampaignCard>) {
        this.campaignList = arrayList
        notifyDataSetChanged()
    }

    inner class CampaignListViewHolder(private val binding: ItemCampaignCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CampaignCard) {
            binding.campaign = item
            binding.executePendingBindings()

            initListener(item)
        }

        private fun initListener(item: CampaignCard) {
            binding.root.setOnClickListener {
                donateViewModel.setSelectedCampaignId(item.campaignId)
                donateViewModel.getCampaignInfo(item.campaignId)
            }
        }
    }
}