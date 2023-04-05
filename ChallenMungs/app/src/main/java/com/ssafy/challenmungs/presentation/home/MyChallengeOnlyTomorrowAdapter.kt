package com.ssafy.challenmungs.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemMyChallengeOnlyTomorrowBinding
import java.text.SimpleDateFormat

class MyChallengeOnlyTomorrowAdapter(private val arrayList: List<Map<String, Any>>) :
    RecyclerView.Adapter<MyChallengeOnlyTomorrowViewHolder>() {

    private lateinit var binding: ItemMyChallengeOnlyTomorrowBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyChallengeOnlyTomorrowViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_my_challenge_only_tomorrow,
            parent,
            false
        )
        return MyChallengeOnlyTomorrowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyChallengeOnlyTomorrowViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int = arrayList.size
}

class MyChallengeOnlyTomorrowViewHolder(private val binding: ItemMyChallengeOnlyTomorrowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(dto: Map<String, Any>) {
        binding.tvTitle.text = dto.get("title").toString()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startMonth = dateFormat.parse(dto.get("startDate").toString()).month
        val startDate = dateFormat.parse(dto.get("startDate").toString()).date
        val endMonth = dateFormat.parse(dto.get("endDate").toString()).month
        val endDate = dateFormat.parse(dto.get("endDate").toString()).date

        binding.tvPeriod.text = "${startMonth}.${startDate} ~ ${endMonth}.${endDate}"

        when (dto.get("challengeType")) {
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
        binding.tvPrice.text = "${dto.get("entryFee")}"
        binding.tvHeadcount.text =
            "${dto.get("currentParticipantCount").toString()}/${dto.get("maxParticipantCount")}"

        initListener()
    }

    private fun initListener() {
        binding.root.setOnClickListener {
            // 상세 페이지로 이동하는 navigation 코드 구현 필요
        }
    }
}