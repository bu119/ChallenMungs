package com.ssafy.challenmungs.presentation.mypage

import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentMyChallengeListBinding
import com.ssafy.challenmungs.domain.entity.challenge.Challenge
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyChallengeListFragment(
    private val position: Int, private val dataList: ArrayList<Challenge>
) : BaseFragment<FragmentMyChallengeListBinding>(R.layout.fragment_my_challenge_list) {

    override fun initView() {
        binding.rvList.adapter = MyChallengeListAdapter(position, dataList)
    }
}