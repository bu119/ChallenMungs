package com.ssafy.challenmungs.presentation.donate

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.getViewDataBinding
import com.ssafy.challenmungs.databinding.ItemCampaignContentContentBinding
import com.ssafy.challenmungs.databinding.ItemCampaignContentImageBinding
import com.ssafy.challenmungs.databinding.ItemCampaignContentTitleBinding
import com.ssafy.challenmungs.domain.entity.campaign.CampaignContent

class CampaignContentAdapter(private val contentList: List<CampaignContent>) :
    RecyclerView.Adapter<CampaignContentAdapter.CampaignContentViewHolder>() {

    enum class ViewType {
        TITLE, CONTENT, IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignContentViewHolder =
        when (viewType) {
            ViewType.TITLE.ordinal -> {
                CampaignContentViewHolder.TitleViewHolder(
                    getViewDataBinding(
                        parent,
                        R.layout.item_campaign_content_title
                    )
                )
            }
            ViewType.CONTENT.ordinal -> {
                CampaignContentViewHolder.ContentViewHolder(
                    getViewDataBinding(
                        parent,
                        R.layout.item_campaign_content_content
                    )
                )
            }
            else -> {
                CampaignContentViewHolder.ImageViewHolder(
                    getViewDataBinding(
                        parent,
                        R.layout.item_campaign_content_image
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: CampaignContentViewHolder, position: Int) {
        holder.bind(contentList[position])
    }

    override fun getItemCount(): Int = contentList.size

    override fun getItemViewType(position: Int): Int = when (contentList[position].type) {
        "bold" -> ViewType.TITLE.ordinal
        "normal" -> ViewType.CONTENT.ordinal
        else -> ViewType.IMAGE.ordinal
    }

    sealed class CampaignContentViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(item: CampaignContent)

        class TitleViewHolder(private val binding: ItemCampaignContentTitleBinding) :
            CampaignContentViewHolder(binding) {

            override fun bind(item: CampaignContent) {
                binding.body = item.body
            }
        }

        class ContentViewHolder(private val binding: ItemCampaignContentContentBinding) :
            CampaignContentViewHolder(binding) {

            override fun bind(item: CampaignContent) {
                binding.body = item.body
            }
        }

        class ImageViewHolder(private val binding: ItemCampaignContentImageBinding) :
            CampaignContentViewHolder(binding) {

            override fun bind(item: CampaignContent) {
                binding.body = item.body
            }
        }
    }
}