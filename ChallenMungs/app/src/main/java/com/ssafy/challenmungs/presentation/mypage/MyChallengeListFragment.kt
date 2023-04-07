package com.ssafy.challenmungs.presentation.mypage

import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentMyChallengeListBinding
import com.ssafy.challenmungs.domain.entity.challenge.Challenge
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyChallengeListFragment(
    private val position: Int, private val dataList: ArrayList<Challenge>
) : BaseFragment<FragmentMyChallengeListBinding>(R.layout.fragment_my_challenge_list) {

    private val challengeViewModel by activityViewModels<ChallengeViewModel>()

    override fun initView() {
        binding.rvList.adapter =
            MyChallengeListAdapter(position, dataList, challengeViewModel)

        observe()
    }

    private fun observe() {
        challengeViewModel.basicTodayList.observe(viewLifecycleOwner) {
            it?.let {
                navigationNavHostFragmentToDestinationFragment(
                    R.id.challenge_basic_fragment
                )
            }
        }
    }
}