package com.ssafy.challenmungs.presentation.auth

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentOnBoardingBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.klaytn.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    private val authViewModel by activityViewModels<AuthViewModel>()
    private val memberViewModel by activityViewModels<MemberViewModel>()
    private val walletViewModel by activityViewModels<WalletViewModel>()

    override fun initView() {
        binding.toolbar.title = getString(R.string.title_member_onboarding)
        initListener()
        observe()
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            hideKeyboard()
            if (authViewModel.accessToken.value != null) {
                authViewModel.requestJoin(binding.tilEtNickname.text.toString())
            }
        }

        binding.toolbar.ivBack.setOnClickListener {
            hideKeyboard()
            popBackStack()
        }
    }

    private fun observe() {
        authViewModel.authType.observe(viewLifecycleOwner) {
            if (it == "member") {
                walletViewModel.createAccount()
                walletViewModel.createAccount()
            }
        }

        walletViewModel.address.observe(viewLifecycleOwner) {
            if (it.size == 2)
                lifecycleScope.launch {
                    memberViewModel.getMemberInfo()
                }
        }

        memberViewModel.memberInfo.observe(viewLifecycleOwner) {
            if (it != null)
                lifecycleScope.launch {
                    val result = authViewModel.setWallet(
                        it.memberId,
                        walletViewModel.address.value!![0],
                        walletViewModel.address.value!![1]
                    )

                    if (result)
                        navigate(OnBoardingFragmentDirections.actionToMainFragment())
                }
        }
    }
}