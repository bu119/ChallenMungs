package com.ssafy.challenmungs.presentation.auth

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentOnBoardingBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.klaytn.WalletViewModel
import kotlinx.coroutines.runBlocking

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    private val authViewModel by activityViewModels<AuthViewModel>()
    private val memberViewModel by activityViewModels<MemberViewModel>()
    private val walletViewModel by activityViewModels<WalletViewModel>()

    override fun initView() {
        binding.toolbar.tvTitle.text = getString(R.string.title_member_onboarding)
        initListener()
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            runBlocking {
                if (authViewModel.accessToken.value != null) {
                    authViewModel.requestJoin(binding.etNickname.text.toString())
                    walletViewModel.createAccount()
                }
            }

            if (authViewModel.authType.value == "member" && walletViewModel.address.value != null) {
                // 생성된 지갑 주소를 가입 계정에 추가하는 api
                Log.d(
                    "OnBoardingFragment",
                    "OnBoardingFragment: ${walletViewModel.address.value}"
                )
            }

//            lifecycleScope.launch {
//                if (authViewModel.accessToken.value != null) {
//                    val flagJoin = authViewModel.requestJoin(binding.etNickname.text.toString())
//
//                    if (flagJoin) {
//                        val flagCreateAccount = walletViewModel.createAccount()
//
//                        if (flagCreateAccount) {
//                            val success = memberViewModel.getMemberInfo()
//
//                            if (success) {
//                                val intent = Intent(activity, MainActivity::class.java)
//                                startActivity(intent)
//                            }
//                        }
//                    }
//                }
//            }
        }
    }
}