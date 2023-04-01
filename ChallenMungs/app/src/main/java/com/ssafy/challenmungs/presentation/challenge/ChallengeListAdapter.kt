package com.ssafy.challenmungs.presentation.challenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemChallengeCardWaitingBinding
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeCard

class ChallengeListAdapter(private val arrayList: ArrayList<ChallengeCard>) :
    RecyclerView.Adapter<ChallengeListAdapter.ChallengeListViewHolder>() {

    private lateinit var binding: ItemChallengeCardWaitingBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeListViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_challenge_card_waiting,
            parent,
            false
        )
        return ChallengeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeListViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int = arrayList.size

    class ChallengeListViewHolder(private val binding: ItemChallengeCardWaitingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChallengeCard) {
            binding.challenge = item
            initListener()
        }

        private fun initListener() {
            binding.root.setOnClickListener {
                // 상세 페이지로 이동하는 navigation 코드 구현 필요
            }
        }
    }
}