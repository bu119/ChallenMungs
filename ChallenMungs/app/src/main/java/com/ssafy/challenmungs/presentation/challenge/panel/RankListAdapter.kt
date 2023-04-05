package com.ssafy.challenmungs.presentation.challenge.panel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemRankBinding
import com.ssafy.challenmungs.domain.entity.challenge.RankDetail

class RankListAdapter :
    ListAdapter<RankDetail, RankListAdapter.RankViewHolder>(diffUtil) {

    lateinit var binding: ItemRankBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_rank, parent, false
        )
        return RankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun submitList(list: MutableList<RankDetail>?) {
        super.submitList(list)
    }

    inner class RankViewHolder(private val binding: ItemRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(rankDetail: RankDetail) {
            binding.rank = rankDetail
        }
    }

    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<RankDetail>() {
            override fun areItemsTheSame(oldItem: RankDetail, newItem: RankDetail): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: RankDetail, newItem: RankDetail): Boolean =
                oldItem == newItem
        }
    }
}