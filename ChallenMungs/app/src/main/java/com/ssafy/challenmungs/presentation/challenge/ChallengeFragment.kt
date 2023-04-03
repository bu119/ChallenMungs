package com.ssafy.challenmungs.presentation.challenge

import android.view.KeyEvent
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.GridItemDecoration
import com.ssafy.challenmungs.databinding.FragmentChallengeBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment

class ChallengeFragment : BaseFragment<FragmentChallengeBinding>(R.layout.fragment_challenge) {

    private val challengeViewModel by activityViewModels<ChallengeViewModel>()
    private val challengeListAdapter by lazy { ChallengeListAdapter(requireContext()) }

    override fun initView() {
        initRecyclerView()
        observe()
        getChallengeList()
    }

    private fun initRecyclerView() {
        binding.rvChallenge.apply {
            challengeListAdapter.submitList(arrayListOf())
            adapter = challengeListAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            addItemDecoration(GridItemDecoration(requireContext(), 2, 15, 10))
        }

        binding.tilEtSearch.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                challengeViewModel.getChallengeList(1, v.toString())
            }
            return@setOnKeyListener true
        }
    }

    private fun observe() {
        challengeViewModel.challengeList.observe(viewLifecycleOwner) {
            it?.let {
                challengeListAdapter.submitList(it)
            }
        }
    }

    private fun getChallengeList() {
        challengeViewModel.getChallengeList(1)
    }
}