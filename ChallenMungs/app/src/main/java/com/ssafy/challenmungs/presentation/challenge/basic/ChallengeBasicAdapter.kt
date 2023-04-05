package com.ssafy.challenmungs.presentation.challenge.basic

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ChallengeBasicAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        ChallengeBasicFragment.ViewType.TODAY.ordinal -> ChallengeBasicTodayFragment()
        ChallengeBasicFragment.ViewType.HISTORY.ordinal -> ChallengeBasicHistoryFragment()
        else -> ChallengeBasicHistoryFragment()
    }
}