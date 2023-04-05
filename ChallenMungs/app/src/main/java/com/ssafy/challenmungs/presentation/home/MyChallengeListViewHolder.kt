package com.ssafy.challenmungs.presentation.home

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemChallengeCardMyOngoingBinding
import java.text.SimpleDateFormat
import java.util.*

class MyChallengeListViewHolder(private val binding: ItemChallengeCardMyOngoingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(dto: Map<String, Any>) {
        binding.tvTitle.text = dto["title"].toString()
        val startDate = formatDate(dto["startDate"].toString())
        val endDate = formatDate(dto["endDate"].toString())

        binding.tvPeriod.text =
            binding.root.context.getString(R.string.content_period, startDate, endDate)

        when (dto["challengeType"]) {
            1 -> {
                binding.tvTag.text = "일반"
            }
            2 -> {
                when (dto["gameType"]) {
                    1 -> {
                        binding.tvTag.text = "판넬(개)"
                    }
                    2 -> {
                        binding.tvTag.text = "판넬(팀)"
                    }
                }
            }
            3 -> {
                binding.tvTag.text = "보물"
            }
        }
    }

    fun initListener(
        dto: Map<String, Any>,
        navigationNavHostFragmentToDestinationFragment: (Int, Int, Long) -> Unit
    ) {
        binding.root.setOnClickListener {
            // 상세 페이지로 이동하는 navigation 코드 구현 필요
            val challengeId = dto["challengeId"]
            navigationNavHostFragmentToDestinationFragment(
                R.id.nav_host,
                R.id.panel_play_fragment,
                challengeId.toString().toLong()
            )
        }
    }

    private fun formatDate(dateString: String): String? {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputDateFormat.parse(dateString)
        val outputDateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
        return date?.let { outputDateFormat.format(it) }
    }
}