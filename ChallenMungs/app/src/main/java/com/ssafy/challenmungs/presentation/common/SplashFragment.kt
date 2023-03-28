package com.ssafy.challenmungs.presentation.common

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentSplashBinding
import com.ssafy.challenmungs.presentation.auth.AuthActivity
import com.ssafy.challenmungs.presentation.auth.MemberViewModel
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val _splashViewTime: Long = 5000
    private val memberViewModel by activityViewModels<MemberViewModel>()

    override fun initView() {
        lifecycleScope.launch {
            var result = false

            if (ApplicationClass.preferences.accessToken != null)
                result = memberViewModel.getMemberInfo()

            val intent = when ("${ApplicationClass.preferences.isFirstRun}, $result") {
                "false, true" -> Intent(requireContext(), HomeActivity::class.java)
                "false, false" -> Intent(requireContext(), AuthActivity::class.java)
                else -> Intent(requireContext(), PermissionFragment::class.java)
            }

            delay(_splashViewTime)
            startActivity(intent)
        }
    }
}