package com.ssafy.challenmungs.presentation.home

import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.databinding.ItemChallengeCardMyOngoingBinding
import java.text.SimpleDateFormat

class MyChallengeListViewHolder(private val binding: ItemChallengeCardMyOngoingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dto: Map<String, Any>) {
        binding.tvTitle.text = dto.get("title").toString()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startMonth = dateFormat.parse(dto.get("startDate").toString()).month
        val startDate = dateFormat.parse(dto.get("startDate").toString()).date
        val endMonth = dateFormat.parse(dto.get("endDate").toString()).month
        val endDate = dateFormat.parse(dto.get("endDate").toString()).date
        binding.tvPeriod.text = "${startMonth}.${startDate} ~ ${endMonth}.${endDate}"
        when (dto.get("challengeType")) {
            1 -> { binding.tvTag.text = "일반" }
            2 -> { binding.tvTag.text = "판넬" }
            3 -> { binding.tvTag.text = "보물" }
        }
        initListener()
    }

    private fun initListener() {
        binding.root.setOnClickListener {
            // 상세 페이지로 이동하는 navigation 코드 구현 필요
        }
    }
}