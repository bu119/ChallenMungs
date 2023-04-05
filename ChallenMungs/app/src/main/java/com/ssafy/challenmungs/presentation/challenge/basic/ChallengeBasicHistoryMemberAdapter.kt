package com.ssafy.challenmungs.presentation.challenge.basic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemChallengeBasicHistoryMemberBinding
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeMember

class ChallengeBasicHistoryMemberAdapter(private var list: List<ChallengeMember>) :
    RecyclerView.Adapter<ChallengeBasicHistoryMemberAdapter.ChallengeBasicHistoryMemberViewHolder>() {

    private lateinit var binding: ItemChallengeBasicHistoryMemberBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChallengeBasicHistoryMemberViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_challenge_basic_history_member,
            parent,
            false
        )
        return ChallengeBasicHistoryMemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeBasicHistoryMemberViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ChallengeBasicHistoryMemberViewHolder(private val binding: ItemChallengeBasicHistoryMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChallengeMember) {
            binding.data = item
        }
    }
}