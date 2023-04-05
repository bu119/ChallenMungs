package com.ssafy.challenmungs.presentation.challenge.basic

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.LinearItemDecoration
import com.ssafy.challenmungs.databinding.FragmentChallengeBasicHistoryBinding
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeBasicHistory
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeMember
import com.ssafy.challenmungs.presentation.base.BaseFragment

class ChallengeBasicHistoryFragment :
    BaseFragment<FragmentChallengeBasicHistoryBinding>(R.layout.fragment_challenge_basic_history) {

    private val memberList = listOf(
        ChallengeMember(
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/1d379d64-9ebb-427a-9934-149948caedadKakaoTalk_20230327_155852745.jpg",
            "구름이짠나인데용왜용"
        ),
        ChallengeMember(
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/89f45477-b0bd-4815-94ce-ef0b536f5283KakaoTalk_20230405_094746356.jpg",
            "강은선"
        ),
        ChallengeMember(
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/c7f70069-68d6-49c4-b560-f5d8a9761198KakaoTalk_20230405_094910738.jpg",
            "이차돌"
        ),
        ChallengeMember(
            "",
            "커피모카"
        ),
    )

    private val historyList = listOf(
        ChallengeBasicHistory(
            1,
            true,
            "강은선",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "03-14",
            false,
            ""
        ),
        ChallengeBasicHistory(
            1,
            false,
            "강은선",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "03-14",
            false,
            ""
        ),
        ChallengeBasicHistory(
            1,
            false,
            "강은선",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "03-14",
            true,
            ""
        ),
        ChallengeBasicHistory(
            1,
            true,
            "강은선",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "03-14",
            true,
            ""
        ),
        ChallengeBasicHistory(
            1,
            true,
            "강은선",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "03-14",
            true,
            ""
        ),
        ChallengeBasicHistory(
            1,
            false,
            "강은선",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "03-14",
            false,
            ""
        ),
        ChallengeBasicHistory(
            1,
            false,
            "강은선",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/2e020302-e560-4b6c-afe6-c262278e559cKakaoTalk_20230405_092027111.jpg",
            "03-14",
            false,
            ""
        ),
    )

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvMembers.apply {
            adapter = ChallengeBasicHistoryMemberAdapter(memberList)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(
                LinearItemDecoration(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    20
                )
            )
        }

        binding.rvChallenge.apply {
            adapter = ChallengeBasicHistoryCertificationAdapter(historyList)
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        }
    }
}