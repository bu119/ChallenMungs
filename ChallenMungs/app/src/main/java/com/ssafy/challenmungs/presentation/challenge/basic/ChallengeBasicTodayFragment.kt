package com.ssafy.challenmungs.presentation.challenge.basic

import android.util.Log
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
    private val challengeBasicTodayAdapter by lazy { ChallengeBasicTodayAdapter(challengeViewModel) }

    override fun initView() {
        initRecyclerView()
        observe()
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

    private fun observe() {
        challengeViewModel.basicTodayList.observe(viewLifecycleOwner) {
            it?.let {
                Log.d("challengeBasicTodayAdapter", "challengeBasicTodayAdapter: $it")
                challengeBasicTodayAdapter.submitList(it)
            }
        }
    }
}