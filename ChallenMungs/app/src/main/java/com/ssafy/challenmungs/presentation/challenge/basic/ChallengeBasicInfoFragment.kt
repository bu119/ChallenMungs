package com.ssafy.challenmungs.presentation.challenge.basic

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.BindingAdapters.setParticipantList
import com.ssafy.challenmungs.databinding.FragmentChallengeBasicInfoBinding
import com.ssafy.challenmungs.domain.entity.challenge.Participant
import com.ssafy.challenmungs.presentation.auth.MemberViewModel
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel
import kotlinx.coroutines.launch

class ChallengeBasicInfoFragment :
    BaseFragment<FragmentChallengeBasicInfoBinding>(R.layout.fragment_challenge_basic_info) {

    private val memberViewModel by activityViewModels<MemberViewModel>()
    private val challengeViewModel by activityViewModels<ChallengeViewModel>()

    override fun initView() {
        initBind()
        initListener()
        observe()
    }

    override fun onDestroy() {
        super.onDestroy()
        challengeViewModel.notStartedChallengeDetail.value?.let {
            if (it.status == 0)
                challengeViewModel.initNotStartedChallengeDetail()
        }
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
            challengeViewModel.notStartedChallengeDetail.value?.let {
                if (it.status == 0)
                    challengeViewModel.initNotStartedChallengeDetail()
            }
            popBackStack()
        }

        binding.btnParticipate.setOnClickListener {
            lifecycleScope.launch {
                challengeViewModel.notStartedChallengeDetail.value?.let {
                    val result =
                        if (!it.isParticipated) challengeViewModel.requestParticipate(it.challengeId.toLong())
                        else challengeViewModel.requestWithDraw(it.challengeId.toLong())

                    if (result) {
                        binding.btnParticipate.text = if (it.isParticipated) "참가하기" else "나가기"

                        challengeViewModel.getChallengeInfo(it.challengeId)
                        challengeViewModel.setChallengeParticipationFlag(!it.isParticipated)
                    }
                }
            }
        }
    }

    private fun observe() {
        challengeViewModel.notStartedChallengeDetail.observe(viewLifecycleOwner) {
            it?.let {
                val myData = Participant(
                    memberViewModel.memberInfo.value!!.profileImageUrl,
                    memberViewModel.memberInfo.value!!.name
                )
                it.participants.forEach { member ->
                    if (member.name == myData.name && member.profileImageUrl == myData.profileImageUrl) it.isParticipated =
                        true
                }
                binding.rvParticipants.setParticipantList(it.participants)
            }
        }
    }
}