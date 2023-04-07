package com.ssafy.challenmungs.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemChallengeCardMyOngoingBinding
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel

class MyChallengeListAdapter(
    private val navigationNavHostFragmentToDestinationFragment: (Int, Long) -> Unit,
    private val data: List<Map<String, Any>>,
    private val challengeViewModel: ChallengeViewModel,
) : RecyclerView.Adapter<MyChallengeListViewHolder>() {

    private lateinit var binding: ItemChallengeCardMyOngoingBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyChallengeListViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_challenge_card_my_ongoing,
            parent,
            false
        )
        return MyChallengeListViewHolder(binding, challengeViewModel)
    }

    override fun onBindViewHolder(holder: MyChallengeListViewHolder, position: Int) {
        with(data[position]) {
            holder.bind(this)
            holder.initListener(this, navigationNavHostFragmentToDestinationFragment)
        }
    }

    override fun getItemCount(): Int = data.size
}