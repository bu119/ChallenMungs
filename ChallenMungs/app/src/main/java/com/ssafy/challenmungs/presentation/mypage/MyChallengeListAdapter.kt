package com.ssafy.challenmungs.presentation.mypage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyChallengeListAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> MyChallengeOngoingFragment()
        1 -> MyChallengeWaitFragment()
        else -> MyChallengeFinishFragment()
    }
}