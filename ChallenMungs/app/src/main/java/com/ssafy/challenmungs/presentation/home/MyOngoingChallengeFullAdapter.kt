package com.ssafy.challenmungs.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemChallengeCardMyOngoingMatchParentBinding
import java.text.SimpleDateFormat

class MyOngoingChallengeFullAdapter(private val arrayList: List<Map<String, Any>>) :
    RecyclerView.Adapter<MyOngoingChallengeFullViewHolder>() {

    private lateinit var binding: ItemChallengeCardMyOngoingMatchParentBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyOngoingChallengeFullViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_challenge_card_my_ongoing_match_parent,
            parent,
            false
        )
        return MyOngoingChallengeFullViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyOngoingChallengeFullViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int = arrayList.size
}

class MyOngoingChallengeFullViewHolder(private val binding: ItemChallengeCardMyOngoingMatchParentBinding) :
RecyclerView.ViewHolder(binding.root) {

    fun bind(dto: Map<String, Any>) {
        binding.tvTitle.text = dto["title"].toString()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startMonth = dateFormat.parse(dto.get("startDate").toString()).month
        val startDate = dateFormat.parse(dto.get("startDate").toString()).date
        val endMonth = dateFormat.parse(dto.get("endDate").toString()).month
        val endDate = dateFormat.parse(dto.get("endDate").toString()).date

        binding.tvPeriod.text = "${startMonth}.${startDate} ~ ${endMonth}.${endDate}"

        when (dto["challengeType"]) {
            1 -> {
                binding.tvTag.text = "일반"
            }
            2 -> {
                when (dto["gameType"]) {
                    1 -> {binding.tvTag.text = "판넬(개)"}
                    2 -> {binding.tvTag.text = "판넬(팀)"}
                }
            }
            3 -> {
                binding.tvTag.text = "보물"
            }
        }

        initListener()
    }

    private fun initListener() {

    }
}