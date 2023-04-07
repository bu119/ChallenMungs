package com.ssafy.challenmungs.presentation.common

import android.content.Context
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.backDoublePressedFragmentCallback
import com.ssafy.challenmungs.databinding.FragmentSplashBinding
import com.ssafy.challenmungs.presentation.auth.AuthActivity
import com.ssafy.challenmungs.presentation.auth.MemberViewModel
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private lateinit var callback: OnBackPressedCallback
    private val _splashViewTime: Long = 3000
    private val memberViewModel by activityViewModels<MemberViewModel>()

    override fun initView() {
        lifecycleScope.launch {
            var result = false

            if (ApplicationClass.preferences.accessToken != null)
                result = memberViewModel.getMemberInfo()

            delay(_splashViewTime)

            if (ApplicationClass.preferences.isFirstRun) {
                navigate(SplashFragmentDirections.actionToPermissionFragment())
            } else if (result) {
                memberViewModel.memberInfo.value?.let {
                    if (it.type == "n")
                        navigate(SplashFragmentDirections.actionToMainFragment())
                    else
                        navigate(SplashFragmentDirections.actionToShelterHomeFragment())
                }
            } else {
                val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = backDoublePressedFragmentCallback(this@SplashFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }
}