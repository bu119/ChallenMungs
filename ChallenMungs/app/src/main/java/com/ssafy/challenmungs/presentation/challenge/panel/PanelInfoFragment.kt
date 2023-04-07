package com.ssafy.challenmungs.presentation.challenge.panel

import android.view.View
import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.BindingAdapters.setRankList
import com.ssafy.challenmungs.databinding.FragmentPanelInfoBinding
import com.ssafy.challenmungs.domain.entity.challenge.RankDetail
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PanelInfoFragment :
    BaseFragment<FragmentPanelInfoBinding>(R.layout.fragment_panel_info) {

    private val panelPlayViewModel by activityViewModels<PanelPlayViewModel>()

    override fun initView() {
        setBind()
    }

    private fun setBind() {
        val isTeamPlay = panelPlayViewModel.challengeInfo.value?.type == "팀전"

        if (isTeamPlay) {
            binding.rvIndividualRank.visibility = View.GONE
            binding.clTeamRankArea.visibility = View.VISIBLE
        } else {
            binding.clTeamRankArea.visibility = View.GONE
            binding.rvIndividualRank.visibility = View.VISIBLE
        }

        panelPlayViewModel.challengeInfo.observe(viewLifecycleOwner) { challengeInfo ->
            binding.info = challengeInfo
            if (isTeamPlay) {
                val firstRank = arrayListOf<RankDetail>()
                val secondRank = arrayListOf<RankDetail>()
                var firstTeamId = 0
                var secondTeamId = 0

                challengeInfo?.currentRank?.let {
                    it.forEach { rankDetail ->
                        when (rankDetail.teamRank) {
                            1 -> {
                                firstRank.add(rankDetail)
                                firstTeamId = rankDetail.teamId
                            }
                            2 -> {
                                secondRank.add(rankDetail)
                                secondTeamId = rankDetail.teamId
                            }
                        }
                    }

                    binding.apply {
                        tvFirstTeam.text = getString(
                            R.string.content_team_rank,
                            1,
                            if (firstTeamId == 1) "A" else "B",
                            panelPlayViewModel.sumPoint.value?.get(firstTeamId)
                        )
                        tvSecondTeam.text = getString(
                            R.string.content_team_rank,
                            2,
                            if (secondTeamId == 1) "A" else "B",
                            panelPlayViewModel.sumPoint.value?.get(secondTeamId)
                        )
                        rvFirstTeamRank.setRankList(firstRank, true)
                        rvSecondTeamRank.setRankList(secondRank, true)
                    }
                }
            } else
                binding.rvIndividualRank.setRankList(challengeInfo?.currentRank, false)
        }

        binding.toolbar.ivBack.setOnClickListener {
            popBackStack()
        }
    }
}