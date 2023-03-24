package com.ssafy.challenmungs.presentation.auth

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentOnBoardingBinding
import com.ssafy.challenmungs.presentation.MainActivity
import com.ssafy.challenmungs.presentation.base.BaseFragment
import kotlinx.coroutines.launch

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    private val authViewModel by activityViewModels<AuthViewModel>()
    private val memberViewModel by activityViewModels<MemberViewModel>()

    override fun initView() {
        binding.toolbar.tvTitle.text = getString(R.string.title_member_onboarding)
        initListener()
        observeAccessToken()
        observeMemberInfo()
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            if (authViewModel.accessToken.value != null)
                authViewModel.requestJoin(binding.etNickname.text.toString())
        }
    }

    private fun observeAccessToken() {
        authViewModel.accessToken.observe(viewLifecycleOwner) {
            checkAccessToken()
        }
    }

    private fun observeMemberInfo() {
        memberViewModel.memberInfo.observe(viewLifecycleOwner) {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkAccessToken() {
        lifecycleScope.launch {
            memberViewModel.getMemberInfo()
        }
    }
}