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
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/916dbaac-1ab0-4c1c-89ec-b18b193dede0KakaoTalk_20230405_094433962_01.jpg",
            "강은선"
        ),
        ChallengeMember(
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/916dbaac-1ab0-4c1c-89ec-b18b193dede0KakaoTalk_20230405_094433962_01.jpg",
            "강은선"
        ),
        ChallengeMember(
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/916dbaac-1ab0-4c1c-89ec-b18b193dede0KakaoTalk_20230405_094433962_01.jpg",
            "강은선"
        ),
        ChallengeMember(
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/916dbaac-1ab0-4c1c-89ec-b18b193dede0KakaoTalk_20230405_094433962_01.jpg",
            "강은선"
        ),
        ChallengeMember(
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/916dbaac-1ab0-4c1c-89ec-b18b193dede0KakaoTalk_20230405_094433962_01.jpg",
            "구름이짠나인데용왜용"
        ),
        ChallengeMember(
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/916dbaac-1ab0-4c1c-89ec-b18b193dede0KakaoTalk_20230405_094433962_01.jpg",
            "구름이짠나인데용왜용"
        ),
        ChallengeMember(
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/916dbaac-1ab0-4c1c-89ec-b18b193dede0KakaoTalk_20230405_094433962_01.jpg",
            "구름이짠나인데용왜용"
        ),
        ChallengeMember(
            "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/916dbaac-1ab0-4c1c-89ec-b18b193dede0KakaoTalk_20230405_094433962_01.jpg",
            "구름이짠나인데용왜용"
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