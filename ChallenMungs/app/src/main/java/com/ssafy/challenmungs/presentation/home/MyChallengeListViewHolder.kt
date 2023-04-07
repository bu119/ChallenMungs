package com.ssafy.challenmungs.presentation.home

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemChallengeCardMyOngoingBinding
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel
import java.text.SimpleDateFormat
import java.util.*

class MyChallengeListViewHolder(
    private val binding: ItemChallengeCardMyOngoingBinding,
    private val challengeViewModel: ChallengeViewModel,
) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(dto: Map<String, Any>) {
        val startDate = formatDate(dto["startDate"].toString())
        val endDate = formatDate(dto["endDate"].toString())

        binding.tvTitle.text = dto["title"].toString()
        binding.tvPeriod.text =
            binding.root.context.getString(R.string.content_period, startDate, endDate)

        when (dto["challengeType"]) {
            1 -> binding.tvTag.text = "일반"
            2 -> when (dto["gameType"]) {
                1 -> binding.tvTag.text = "판넬(개)"
                2 -> binding.tvTag.text = "판넬(팀)"
            }
            3 -> binding.tvTag.text = "보물"
        }
    }

    fun initListener(
        dto: Map<String, Any>,
        navigationNavHostFragmentToDestinationFragment: (Int, Long) -> Unit
    ) {
        binding.root.setOnClickListener {
            // 상세 페이지로 이동하는 navigation 코드 구현 필요
            val challengeId = dto["challengeId"]
            if (dto["challengeType"].toString().toInt() == 2)
                navigationNavHostFragmentToDestinationFragment(
                    R.id.panel_play_fragment,
                    challengeId.toString().toLong()
                )
            else {
                challengeId.toString().toInt().let {
                    challengeViewModel.getParticipants(it)
                    challengeViewModel.getChallengeInfo(it)
                    challengeViewModel.getBasicToday(it)
                }
            }
        }
    }

    private fun formatDate(dateString: String): String? {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputDateFormat.parse(dateString)
        val outputDateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())

        return date?.let { outputDateFormat.format(it) }
    }
}