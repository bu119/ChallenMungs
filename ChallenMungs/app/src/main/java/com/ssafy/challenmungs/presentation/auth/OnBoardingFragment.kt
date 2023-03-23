package com.ssafy.challenmungs.presentation.auth

import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentOnBoardingBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun initView() {
        binding.toolbar.tvTitle.text = getString(R.string.title_member_onboarding)
        initListener()
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            // 회원가입 api 요청, AuthViewModel 의 email 필요함.
            val name = binding.etNickname.text
        }
    }
}