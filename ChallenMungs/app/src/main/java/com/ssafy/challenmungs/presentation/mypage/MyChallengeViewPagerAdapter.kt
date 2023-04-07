package com.ssafy.challenmungs.presentation.mypage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyChallengeViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        ChallengeState.ONGOING.ordinal -> MyChallengeListFragment(position, arrayListOf())
        ChallengeState.WAIT.ordinal -> MyChallengeListFragment(position, arrayListOf())
        else -> MyChallengeListFragment(position, arrayListOf())
    }
}