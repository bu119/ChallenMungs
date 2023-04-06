package com.ssafy.challenmungs.presentation.challenge.basic

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.LinearItemDecoration
import com.ssafy.challenmungs.databinding.FragmentChallengeBasicTodayBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel

class ChallengeBasicTodayFragment :
    BaseFragment<FragmentChallengeBasicTodayBinding>(R.layout.fragment_challenge_basic_today) {

    private val challengeViewModel by activityViewModels<ChallengeViewModel>()
    private val challengeBasicTodayAdapter by lazy { ChallengeBasicTodayAdapter() }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvToday.apply {
            adapter = challengeBasicTodayAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                LinearItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    20
                )
            )

            challengeBasicTodayAdapter.submitList(challengeViewModel.basicTodayList.value)
        }
    }
}