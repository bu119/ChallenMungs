package com.ssafy.challenmungs.presentation.challenge.basic

import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.LinearItemDecoration
import com.ssafy.challenmungs.databinding.FragmentChallengeBasicTodayBinding
import com.ssafy.challenmungs.domain.entity.challenge.ChallengeBasicToday
import com.ssafy.challenmungs.presentation.base.BaseFragment

class ChallengeBasicTodayFragment :
    BaseFragment<FragmentChallengeBasicTodayBinding>(R.layout.fragment_challenge_basic_today) {

    private val challengeBasicTodayAdapter by lazy { ChallengeBasicTodayAdapter() }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvToday.apply {
            adapter = challengeBasicTodayAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                LinearItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    20
                )
            )

            challengeBasicTodayAdapter.submitList(
                listOf(
                    ChallengeBasicToday(
                        1,
                        "구름이짠나인데용왜용",
                        "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/1d379d64-9ebb-427a-9934-149948caedadKakaoTalk_20230327_155852745.jpg",
                        "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/1d379d64-9ebb-427a-9934-149948caedadKakaoTalk_20230327_155852745.jpg"
                    ),
                    ChallengeBasicToday(
                        1,
                        "강은선",
                        "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/89f45477-b0bd-4815-94ce-ef0b536f5283KakaoTalk_20230405_094746356.jpg",
                        "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/89f45477-b0bd-4815-94ce-ef0b536f5283KakaoTalk_20230405_094746356.jpg"
                    ),
                    ChallengeBasicToday(
                        1,
                        "이차돌",
                        "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/1d379d64-9ebb-427a-9934-149948caedadKakaoTalk_20230327_155852745.jpg",
                        "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/1d379d64-9ebb-427a-9934-149948caedadKakaoTalk_20230327_155852745.jpg"
                    ),
                    ChallengeBasicToday(
                        1,
                        "카페모카",
                        "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/1d379d64-9ebb-427a-9934-149948caedadKakaoTalk_20230327_155852745.jpg",
                        "https://kr.object.ncloudstorage.com/challenmungs-storage/challenge/1d379d64-9ebb-427a-9934-149948caedadKakaoTalk_20230327_155852745.jpg"
                    ),
                )
            )
        }
    }
}