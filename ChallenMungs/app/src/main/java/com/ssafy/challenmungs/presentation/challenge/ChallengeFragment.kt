package com.ssafy.challenmungs.presentation.challenge

import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentChallengeBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment

class ChallengeFragment : BaseFragment<FragmentChallengeBinding>(R.layout.fragment_challenge) {

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvChallenge.apply {
            adapter = ChallengeListAdapter(arrayListOf())
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }
}