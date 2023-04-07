package com.ssafy.challenmungs.presentation.challenge

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemChallengeCardWaitingBinding
import com.ssafy.challenmungs.domain.entity.challenge.Challenge

class ChallengeListAdapter(
    private val context: Context,
    private val getChallengeInfo: (Int) -> Unit
) :
    RecyclerView.Adapter<ChallengeListAdapter.ChallengeListViewHolder>() {

    private lateinit var binding: ItemChallengeCardWaitingBinding
    private lateinit var challengeList: List<Challenge>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_challenge_card_waiting,
            parent,
            false
        )
        return ChallengeListViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        holder.bind(challengeList[position])
    }

    override fun getItemCount(): Int = challengeList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Challenge>) {
        challengeList = list
        notifyDataSetChanged()
    }

    inner class ChallengeListViewHolder(
        private val context: Context,
        private val binding: ItemChallengeCardWaitingBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Challenge) {
            binding.apply {
                challenge = item

                when (item.challengeType) {
                    "일반" -> tvTag.setBackgroundResource(R.drawable.bg_rect_golden_glow_radius5)
                    "판넬(개)" -> tvTag.setBackgroundResource(R.drawable.bg_rect_golden_poppy_radius5)
                    "판넬(팀)" -> tvTag.setBackgroundResource(R.drawable.bg_rect_golden_poppy_radius5)
                    "보물" -> {
                        tvTag.setBackgroundResource(R.drawable.bg_rect_toast_radius5)
                        tvTag.setTextColor(context.getColor(R.color.white))
                    }
                }

                executePendingBindings()
            }

            initListener(item.challengeId)
        }

        private fun initListener(challengeId: Int) {
            binding.root.setOnClickListener {
                getChallengeInfo(challengeId)
            }
        }
    }
}