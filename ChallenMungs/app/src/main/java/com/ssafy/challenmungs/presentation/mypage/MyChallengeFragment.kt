package com.ssafy.challenmungs.presentation.mypage

import android.graphics.Typeface
import android.widget.TextView
import androidx.core.view.children
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentMyChallengeBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyChallengeFragment :
    BaseFragment<FragmentMyChallengeBinding>(R.layout.fragment_my_challenge) {

    override fun initView() {
        binding.apply {
            toolbar.tvTitle.text = getString(R.string.title_my_challenge)
            tlChallengeState.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                fun setStyleForTab(tab: TabLayout.Tab, style: Int) {
                    tab.view.children.find { it is TextView }?.let { tv ->
                        (tv as TextView).post {
                            tv.setTypeface(null, style)
                        }
                    }
                }
            })
            vpChallengeList.adapter = MyChallengeViewPagerAdapter(this@MyChallengeFragment)
            val tabTitles = listOf(
                getString(R.string.title_ongoing),
                getString(R.string.title_wait),
                getString(R.string.title_finish)
            )
            TabLayoutMediator(
                tlChallengeState,
                vpChallengeList
            ) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }
}