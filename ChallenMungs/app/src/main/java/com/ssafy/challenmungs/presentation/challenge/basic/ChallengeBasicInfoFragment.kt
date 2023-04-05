package com.ssafy.challenmungs.presentation.challenge.basic

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentChallengeBasicInfoBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel

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
        binding.info = challengeViewModel.notStartedChallengeDetail.value

        binding.toolbar.ivBack.setOnClickListener {
            challengeViewModel.initNotStartedChallengeDetail()
            popBackStack()
        }
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
}