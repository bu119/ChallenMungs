package com.ssafy.challenmungs.presentation.challenge.basic

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemChallengeBasicHistoryCertificationBinding
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeBasicHistory

class ChallengeBasicHistoryCertificationAdapter(private var list: List<ChallengeBasicHistory>) :
    RecyclerView.Adapter<ChallengeBasicHistoryCertificationAdapter.ChallengeBasicHistoryCertificationViewHolder>() {

    private lateinit var binding: ItemChallengeBasicHistoryCertificationBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChallengeBasicHistoryCertificationViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_challenge_basic_history_certification,
            parent,
            false
        )
        return ChallengeBasicHistoryCertificationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ChallengeBasicHistoryCertificationViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ChallengeBasicHistory>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ChallengeBasicHistoryCertificationViewHolder(private val binding: ItemChallengeBasicHistoryCertificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChallengeBasicHistory) {
            binding.date = item
            binding.tvDate.visibility = if (item.pictureUrl == "") View.VISIBLE else View.GONE
            binding.ivReject.visibility = if (item.rejectResult) View.VISIBLE else View.GONE
            binding.btnCertificate.visibility =
                if (adapterPosition == 0 && list[0].pictureUrl == "") View.VISIBLE else View.GONE
        }
    }
}