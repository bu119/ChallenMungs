package com.ssafy.challenmungs.presentation.auth

import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentOnBoardingBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.home.HomeActivity
import com.ssafy.challenmungs.presentation.klaytn.WalletViewModel

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    private val authViewModel by activityViewModels<AuthViewModel>()
    private val walletViewModel by activityViewModels<WalletViewModel>()

    override fun initView() {
        binding.toolbar.tvTitle.text = getString(R.string.title_member_onboarding)
        initListener()
        observe()
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            if (authViewModel.accessToken.value != null) {
                authViewModel.requestJoin(binding.etNickname.text.toString())
            }
        }
    }

    private fun observe() {
        authViewModel.authType.observe(viewLifecycleOwner) {
            if (authViewModel.authType.value == "member") {
                walletViewModel.createAccount()
                walletViewModel.createAccount()
            }
        }

        walletViewModel.address.observe(viewLifecycleOwner) {
            if (walletViewModel.address.value != null)
                moveToHomeActivity()
        }
    }

    private fun moveToHomeActivity() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
    }
}