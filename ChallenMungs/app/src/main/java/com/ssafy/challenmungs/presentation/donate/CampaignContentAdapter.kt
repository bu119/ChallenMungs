package com.ssafy.challenmungs.presentation.donate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemCampaignContentBoldBinding
import com.ssafy.challenmungs.databinding.ItemCampaignContentImageBinding
import com.ssafy.challenmungs.databinding.ItemCampaignContentNormalBinding
import com.ssafy.challenmungs.domain.entity.campaign.CampaignContent

class CampaignContentAdapter(private var list: List<CampaignContent>) :
    RecyclerView.Adapter<CampaignContentAdapter.CampaignContentViewHolder>() {

    enum class ContentType {
        BOLD, NORMAL, IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignContentViewHolder =
        when (viewType) {
            ContentType.BOLD.ordinal ->
                CampaignContentViewHolder.BoldViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_campaign_content_bold,
                        parent,
                        false
                    )
                )
            ContentType.NORMAL.ordinal ->
                CampaignContentViewHolder.NormalViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_campaign_content_normal,
                        parent,
                        false
                    )
                )
            else ->
                CampaignContentViewHolder.ImageViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_campaign_content_image,
                        parent,
                        false
                    )
                )

        }

    override fun onBindViewHolder(holder: CampaignContentViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int =
        when (list[position].type) {
            "bold" -> ContentType.BOLD.ordinal
            "normal" -> ContentType.NORMAL.ordinal
            else -> ContentType.IMAGE.ordinal
        }

    sealed class CampaignContentViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(data: CampaignContent)

        class BoldViewHolder(private val binding: ItemCampaignContentBoldBinding) :
            CampaignContentViewHolder(binding) {

            override fun bind(data: CampaignContent) {
                binding.content = data
            }
        }

        class NormalViewHolder(private val binding: ItemCampaignContentNormalBinding) :
            CampaignContentViewHolder(binding) {

            override fun bind(data: CampaignContent) {
                binding.content = data
            }
        }

        class ImageViewHolder(private val binding: ItemCampaignContentImageBinding) :
            CampaignContentViewHolder(binding) {

            override fun bind(data: CampaignContent) {
                binding.content = data
            }
        }

    }
}