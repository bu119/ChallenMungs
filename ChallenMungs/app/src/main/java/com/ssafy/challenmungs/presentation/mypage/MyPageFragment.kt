package com.ssafy.challenmungs.presentation.mypage

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.BindingAdapters.setProfileImg
import com.ssafy.challenmungs.databinding.FragmentMyPageBinding
import com.ssafy.challenmungs.presentation.auth.MemberViewModel
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.common.CustomSimpleDialog
import com.ssafy.challenmungs.presentation.common.CustomSimpleDialogInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page),
    CustomSimpleDialogInterface {

    private val myPageViewModel by activityViewModels<MyPageViewModel>()
    private val memberViewModel by activityViewModels<MemberViewModel>()

    override fun initView() {
        binding.tvLogout.setOnClickListener {
            val dialog = CustomSimpleDialog(
                requireContext(),
                this@MyPageFragment,
                false,
                "로그아웃 하시겠어요?",
                "확인"
            )
            dialog.show()
        }
        initData()
        initListener()
    }

    private fun setBind() {
        myPageViewModel.totalDonate.observe(viewLifecycleOwner) {
            binding.tvTotalContribution.text = getString(R.string.content_total_contribution, it)
        }

        myPageViewModel.myWalletBalance.observe(viewLifecycleOwner) {
            binding.tvMyWalletAmount.text = it
        }

        myPageViewModel.piggyBankBalance.observe(viewLifecycleOwner) {
            binding.tvDonateBankAmount.text = it
        }

        myPageViewModel.heartTemperature.observe(viewLifecycleOwner) {
            binding.apply {
                llHeartTemperature.visibility = View.VISIBLE

                val animation = ObjectAnimator.ofInt(progressBar, "progress", 0, it).apply {
                    duration = 2000
                    interpolator = LinearInterpolator()
                }
                val pivotX = progressBar.width * (it / 100).toFloat()
                val translateAnimation =
                    TranslateAnimation(0f, pivotX, 0f, 0f).apply {
                        duration = 2000
                        interpolator = LinearInterpolator()
                        setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation?) {}

                            override fun onAnimationEnd(animation: Animation?) {
                                if (llHeartTemperature.x < pivotX)
                                    llHeartTemperature.x = pivotX
                            }

                            override fun onAnimationRepeat(animation: Animation?) {}
                        })
                    }

                progressBar.progress = it
                progressBar.isIndeterminate = false
                tvHeartTemperature.text = getString(R.string.content_heart_temperature_amount, it)
                llHeartTemperature.animation = translateAnimation

                animation.start()
                translateAnimation.start()
            }
        }

        memberViewModel.memberInfo.observe(viewLifecycleOwner) {
            binding.tvNickname.text = it?.name
            binding.ivProfile.setProfileImg(it?.profileImageUrl)
        }
    }

    private fun initData() {
        lifecycleScope.launch {
            myPageViewModel.getTotalDonate()
            myPageViewModel.getMyWalletBalance("w")
            myPageViewModel.getMyWalletBalance("p")
            setBind()
        }
    }

    private fun initListener() {
        binding.apply {
            clUserArea.setOnClickListener {
                navigationNavHostFragmentToDestinationFragment(
                    R.id.edit_profile_fragment
                )
            }

            clMyWallet.setOnClickListener {
                navigationNavHostFragmentToDestinationFragment(
                    R.id.my_wallet_fragment
                )
            }

            clBank.setOnClickListener {
                navigationNavHostFragmentToDestinationFragment(
                    R.id.piggy_bank_fragment
                )
            }

            clListMyChallenge.setOnClickListener {
                navigationNavHostFragmentToDestinationFragment(
                    R.id.my_challenge_fragment
                )
            }

            clListCheerCampaign.setOnClickListener {
                navigationNavHostFragmentToDestinationFragment(
                    R.id.my_cheer_campaign_fragment
                )
            }

            clListDonateHistory.setOnClickListener {
                navigationNavHostFragmentToDestinationFragment(
                    R.id.donation_details_fragment
                )
            }

            clListMyCampaign.setOnClickListener {
                navigationNavHostFragmentToDestinationFragment(
                    R.id.my_donate_campaign_fragment
                )
            }
        }
    }

    override fun onPositiveButton() {
        ApplicationClass.preferences.clearPreferences()
        requireActivity().finish()
    }
}