package com.ssafy.challenmungs.presentation.challenge.basic

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentChallengeBasicBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel

class ChallengeBasicFragment :
    BaseFragment<FragmentChallengeBasicBinding>(R.layout.fragment_challenge_basic) {

    private val challengeViewModel by activityViewModels<ChallengeViewModel>()
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            challengeViewModel.initBasicTodayList()
            popBackStack()
        }
    }

    enum class ViewType {
        TODAY, HISTORY
    }

    override fun initView() {
        Log.d("ChallengeBasicFragment", "ChallengeBasicFragment is init")
        observe()
        initViewPager()
        initListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.onBackPressedDispatcher?.addCallback(this@ChallengeBasicFragment, callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }

    private fun initViewPager() {
        val tabTitles = listOf(
            getString(R.string.title_challenge_basic_today),
            getString(R.string.title_challenge_basic_history)
        )

        binding.apply {
            vpChallengeBasic.adapter = ChallengeBasicAdapter(this@ChallengeBasicFragment)

            TabLayoutMediator(tlChallengeMenu, vpChallengeBasic) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    private fun initListener() {
        binding.tlChallengeMenu.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    setStyleForTab(it, Typeface.BOLD)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.let {
                    setStyleForTab(it, Typeface.NORMAL)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            fun setStyleForTab(tab: TabLayout.Tab, style: Int) {
                tab.view.children.find { it is TextView }?.let { tv ->
                    (tv as TextView).post {
                        tv.setTypeface(null, style)
                    }
                }
            }
        })

        binding.toolbar.tvInfo.setOnClickListener {
            navigationNavHostFragmentToDestinationFragment(
                R.id.challenge_basic_info_fragment
            )
        }

        binding.toolbar.ivBack.setOnClickListener {
            challengeViewModel.initNotStartedChallengeDetail()
            challengeViewModel.initBasicTodayList()
            popBackStack()
        }
    }

    private fun observe() {
        challengeViewModel.notStartedChallengeDetail.observe(viewLifecycleOwner) {
            it?.let {
                binding.toolbar.title = challengeViewModel.notStartedChallengeDetail.value!!.title

                if (challengeViewModel.participants.value != null)
                    challengeViewModel.getBasicHistory(
                        it.challengeId,
                        challengeViewModel.participants.value!![0].memberId
                    )
            }
        }
    }
}