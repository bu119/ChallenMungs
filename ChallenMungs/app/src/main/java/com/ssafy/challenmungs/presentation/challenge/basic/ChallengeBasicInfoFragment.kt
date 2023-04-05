package com.ssafy.challenmungs.presentation.challenge.basic

import android.content.Context
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentChallengeBasicInfoBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel
import kotlinx.coroutines.launch

class ChallengeBasicInfoFragment :
    BaseFragment<FragmentChallengeBasicInfoBinding>(R.layout.fragment_challenge_basic_info) {

    private val challengeViewModel by activityViewModels<ChallengeViewModel>()
    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            challengeViewModel.initNotStartedChallengeDetail()
            popBackStack()
        }
    }

    override fun initView() {
        initBind()
        initListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(
            this@ChallengeBasicInfoFragment,
            callback
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }

    private fun initBind() {
        binding.apply {
            info = challengeViewModel.notStartedChallengeDetail.value

            challengeViewModel.notStartedChallengeDetail.value?.let {
                if (it.status == 0)
                    btnParticipate.text = if (it.isParticipated) "나가기" else "참가하기"
                else
                    btnParticipate.visibility = View.GONE
            }
        }
    }

    private fun initListener() {
        binding.toolbar.ivBack.setOnClickListener {
            challengeViewModel.initNotStartedChallengeDetail()
            popBackStack()
        }

        binding.btnParticipate.setOnClickListener {
            lifecycleScope.launch {
                challengeViewModel.notStartedChallengeDetail.value?.let {
                    if (!it.isParticipated) {
                        val result =
                            challengeViewModel.requestParticipate(challengeViewModel.notStartedChallengeDetail.value!!.challengeId.toLong())

                        if (result) {
                            binding.btnParticipate.text = "나가기"
                            challengeViewModel.setChallengeParticipationFlag(false)
                        }
                    }
                }
            }
        }
    }
}