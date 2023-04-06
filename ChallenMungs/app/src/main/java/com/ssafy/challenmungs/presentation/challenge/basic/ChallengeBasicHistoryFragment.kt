package com.ssafy.challenmungs.presentation.challenge.basic

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.LinearItemDecoration
import com.ssafy.challenmungs.databinding.FragmentChallengeBasicHistoryBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel

class ChallengeBasicHistoryFragment :
    BaseFragment<FragmentChallengeBasicHistoryBinding>(R.layout.fragment_challenge_basic_history) {

    private val challengeViewModel by activityViewModels<ChallengeViewModel>()
    private val challengeBasicHistoryMemberAdapter by lazy {
        ChallengeBasicHistoryMemberAdapter(
            if (challengeViewModel.participants.value != null) challengeViewModel.participants.value!! else listOf(),
            challengeViewModel::getBasicHistory
        )
    }
    private val challengeBasicHistoryCertificationAdapter by lazy {
        ChallengeBasicHistoryCertificationAdapter(
            if (challengeViewModel.basicTodayHistory.value != null) challengeViewModel.basicTodayHistory.value!! else listOf()
        )
    }

    override fun initView() {
        initRecyclerView()
        observe()
    }

    private fun initRecyclerView() {
        binding.rvMembers.apply {
            adapter = challengeBasicHistoryMemberAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(
                LinearItemDecoration(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    20
                )
            )
        }

        binding.rvChallenge.apply {
            adapter = challengeBasicHistoryCertificationAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun observe() {
        challengeViewModel.participants.observe(viewLifecycleOwner) {
            it?.let { challengeBasicHistoryMemberAdapter.submitList(it) }
        }

        challengeViewModel.basicTodayHistory.observe(viewLifecycleOwner) {
            it?.let { challengeBasicHistoryCertificationAdapter.submitList(it) }
        }
    }
}