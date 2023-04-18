package com.ssafy.challenmungs.presentation.challenge.basic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemChallengeBasicHistoryMemberBinding
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeMember

class ChallengeBasicHistoryMemberAdapter(
    private var list: List<ChallengeMember>,
    private val getBasicHistory: (Int, String) -> Unit
) : ListAdapter<ChallengeMember, ChallengeBasicHistoryMemberAdapter.ChallengeBasicHistoryMemberViewHolder>(
    diffCallback
) {

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
            binding.ivProfileStroke.visibility = if (item.selected) View.VISIBLE else View.GONE

            binding.root.setOnClickListener {
                getBasicHistory(item.challengeId, item.memberId)
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ChallengeMember>() {
            override fun areItemsTheSame(
                oldItem: ChallengeMember,
                newItem: ChallengeMember
            ): Boolean = oldItem.challengeId == newItem.challengeId

            override fun areContentsTheSame(
                oldItem: ChallengeMember,
                newItem: ChallengeMember
            ): Boolean = oldItem.selected == newItem.selected
        }
    }
}