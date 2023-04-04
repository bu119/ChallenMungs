package com.ssafy.challenmungs.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemChallengeCardOngoingFinishBinding
import com.ssafy.challenmungs.databinding.ItemChallengeCardWaitingBinding
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeCard

class MyChallengeListAdapter(
    private val position: Int, private val dataList: ArrayList<ChallengeCard>
) : RecyclerView.Adapter<MyChallengeListAdapter.MyChallengeListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyChallengeListViewHolder = when (position) {
        // 대기 챌린지
        ChallengeState.WAIT.ordinal -> {
            MyChallengeListViewHolder.WaitViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_challenge_card_waiting,
                    parent,
                    false
                )
            )
        }
        else -> {
            // 진행, 완료 챌린지
            MyChallengeListViewHolder.OnGoingOrFinishViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_challenge_card_ongoing_finish,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: MyChallengeListViewHolder, position: Int) {
        holder.onBind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    sealed class MyChallengeListViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        abstract fun onBind(data: ChallengeCard)

        class OnGoingOrFinishViewHolder(private val binding: ItemChallengeCardOngoingFinishBinding) :
            MyChallengeListViewHolder(binding) {

            override fun onBind(data: ChallengeCard) {
                binding.challenge = data
            }
        }

        class WaitViewHolder(private val binding: ItemChallengeCardWaitingBinding) :
            MyChallengeListViewHolder(binding) {

            override fun onBind(data: ChallengeCard) {
                binding.challenge = data
            }
        }
    }
}