package com.ssafy.challenmungs.presentation.challenge.basic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.extractIdFromJWT
import com.ssafy.challenmungs.databinding.ItemChallengeBasicTodayBinding
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeBasicToday
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengeBasicTodayAdapter(private val challengeViewModel: ChallengeViewModel) :
    ListAdapter<ChallengeBasicToday, ChallengeBasicTodayAdapter.ChallengeBasicTodayViewHolder>(
        diffCallback
    ) {

    private lateinit var binding: ItemChallengeBasicTodayBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChallengeBasicTodayViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_challenge_basic_today,
            parent,
            false
        )
        return ChallengeBasicTodayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeBasicTodayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChallengeBasicTodayViewHolder(private val binding: ItemChallengeBasicTodayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChallengeBasicToday) {
            binding.data = item
            binding.tvReject.text = if (item.myRejectState) "취소하기" else "반려하기"
            binding.btnReject.visibility =
                if (extractIdFromJWT(ApplicationClass.preferences.accessToken!!) == item.memberId) View.GONE else View.VISIBLE

            binding.btnReject.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val result = challengeViewModel.requestReject(item.boardId)
                    if (result) {
                        challengeViewModel.getBasicToday(challengeViewModel.notStartedChallengeDetail.value!!.challengeId)
                    }
                }
            }

        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ChallengeBasicToday>() {
            override fun areItemsTheSame(
                oldItem: ChallengeBasicToday,
                newItem: ChallengeBasicToday
            ): Boolean {
                return oldItem.boardId == newItem.boardId
            }

            override fun areContentsTheSame(
                oldItem: ChallengeBasicToday,
                newItem: ChallengeBasicToday
            ): Boolean {
                return oldItem.boardId == newItem.boardId
                        && oldItem.profileUrl == newItem.profileUrl
                        && oldItem.memberName == newItem.memberName
                        && oldItem.challengeImageUrl == newItem.challengeImageUrl
                        && oldItem.myRejectState == newItem.myRejectState
            }
        }
    }
}