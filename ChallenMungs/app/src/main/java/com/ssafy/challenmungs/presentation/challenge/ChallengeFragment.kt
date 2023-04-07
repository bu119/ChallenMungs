package com.ssafy.challenmungs.presentation.challenge

import android.animation.ObjectAnimator
import android.util.Log
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.GridItemDecoration
import com.ssafy.challenmungs.databinding.FragmentChallengeBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeFragment : BaseFragment<FragmentChallengeBinding>(R.layout.fragment_challenge) {

    private val challengeViewModel by activityViewModels<ChallengeViewModel>()
    private val challengeListAdapter by lazy {
        ChallengeListAdapter(
            requireContext(),
            challengeViewModel::getChallengeInfo,
        )
    }
    private var isOpened = false

    override fun initView() {
        initRecyclerView()
        observe()
        initListener()
        challengeViewModel.getChallengeList(1)
    }

    private fun initListener() {
        binding.fabPanel.setOnClickListener {
            navigationNavHostFragmentToDestinationFragment(
                R.id.panel_create_fragment
            )
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            val fabList = listOf(fabBasic, fabPanel)

            rvChallenge.apply {
                challengeListAdapter.submitList(arrayListOf())
                adapter = challengeListAdapter
                layoutManager =
                    GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                addItemDecoration(GridItemDecoration(requireContext(), 2, 15, 10))
            }

            tilEtSearch.apply {
                setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        hideKeyboard()
                        root.requestFocus()
                        challengeViewModel.getChallengeList(1, text.toString())
                        return@setOnKeyListener true
                    } else return@setOnKeyListener false
                }
            }

            fabMain.setOnClickListener {
                isOpened = !isOpened

                if (isOpened) {
                    clFab.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.trans35_black
                        )
                    )
                    fabMain.setImageResource(R.drawable.ic_fab_clicked)

                    fabList.forEachIndexed { index, floatingActionButton ->
                        floatingActionButton.isClickable = true
                        fabOut(floatingActionButton, index).start()
                    }
                } else {
                    clFab.background = null
                    fabMain.setImageResource(R.drawable.ic_plus)

                    fabList.forEach { floatingActionButton ->
                        floatingActionButton.isClickable = false
                        fabIn(floatingActionButton).start()
                    }
                }
            }
        }
    }

    private fun observe() {
        challengeViewModel.challengeList.observe(viewLifecycleOwner) {
            it?.let {
                challengeListAdapter.submitList(it)
            }
        }

        challengeViewModel.notStartedChallengeDetail.observe(viewLifecycleOwner) {
            it?.let {
                lifecycleScope.launch {
                    val result =
                        challengeViewModel.getChallengeParticipationFlag(it.challengeId.toLong())

                    if (result) {
                        Log.d("TAG", "observe: $it")
                        navigationNavHostFragmentToDestinationFragment(
                            R.id.challenge_basic_info_fragment
                        )
                    }
                }
            }
        }
    }

    private fun fabIn(btn: FloatingActionButton): ObjectAnimator =
        ObjectAnimator.ofFloat(btn, "translationY", 0f).setDuration(500)

    private fun fabOut(btn: FloatingActionButton, index: Int): ObjectAnimator =
        ObjectAnimator.ofFloat(btn, "translationY", -190f - 150f * index).setDuration(500)
}