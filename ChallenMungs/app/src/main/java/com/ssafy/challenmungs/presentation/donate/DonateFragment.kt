package com.ssafy.challenmungs.presentation.donate

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.CustomFilterChip.State
import com.ssafy.challenmungs.common.util.GridItemDecoration
import com.ssafy.challenmungs.databinding.FragmentDonateBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonateFragment : BaseFragment<FragmentDonateBinding>(R.layout.fragment_donate) {

    private val donateViewModel by activityViewModels<DonateViewModel>()
    private val campaignListAdapter by lazy { CampaignListAdapter(donateViewModel) }

    override fun initView() {
        observe()
        getCampaignList("default", 1)
        initRecyclerView()
        initChips()
    }

    private fun initRecyclerView() {
        binding.rvCampaign.apply {
            campaignListAdapter.submitList(arrayListOf())
            adapter = campaignListAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            addItemDecoration(GridItemDecoration(requireContext(), 2, 40, 10))
        }
    }

    private fun initChips() {
        with(binding.cfcRecent) {
            setOnClickListener {
                when (state) {
                    State.NONE -> {
                        getCampaignList("date", 1)
                        changeState(State.DESC)
                    }
                    State.DESC -> {
                        getCampaignList("date", 0)
                        changeState(State.ASC)
                    }
                    State.ASC -> {
                        getCampaignList("default", 1)
                        changeState(State.NONE)
                    }
                }

                binding.cfcAmount.changeState(State.NONE)
                binding.cfcCheerUp.changeState(State.NONE)
            }
        }

        with(binding.cfcAmount) {
            setOnClickListener {
                when (state) {
                    State.NONE -> {
                        getCampaignList("amount", 1)
                        changeState(State.DESC)
                    }
                    State.DESC -> {
                        getCampaignList("amount", 0)
                        changeState(State.ASC)
                    }
                    State.ASC -> {
                        getCampaignList("default", 1)
                        changeState(State.NONE)
                    }
                }

                binding.cfcRecent.changeState(State.NONE)
                binding.cfcCheerUp.changeState(State.NONE)
            }
        }

        with(binding.cfcCheerUp) {
            setOnClickListener {
                when (state) {
                    State.NONE -> {
                        getCampaignList("love", 1)
                        changeState(State.DESC)
                    }
                    State.DESC -> {
                        getCampaignList("love", 0)
                        changeState(State.ASC)
                    }
                    State.ASC -> {
                        getCampaignList("default", 1)
                        changeState(State.NONE)
                    }
                }

                binding.cfcRecent.changeState(State.NONE)
                binding.cfcAmount.changeState(State.NONE)
            }
        }
    }

    private fun observe() {
        donateViewModel.campaignList.observe(viewLifecycleOwner) {
            it?.let {
                campaignListAdapter.submitList(it)
            }
        }

        donateViewModel.campaignInfo.observe(viewLifecycleOwner) {
            it?.let {
                navigationNavHostFragmentToDestinationFragment(R.id.campaign_info_fragment)
            }
        }
    }

    private fun getCampaignList(type: String, sort: Int) {
        donateViewModel.getCampaignList(type, sort)
    }
}