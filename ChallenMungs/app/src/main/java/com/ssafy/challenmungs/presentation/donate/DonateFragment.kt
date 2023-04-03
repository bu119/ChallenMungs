package com.ssafy.challenmungs.presentation.donate

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.CustomFilterChip
import com.ssafy.challenmungs.common.util.GridItemDecoration
import com.ssafy.challenmungs.databinding.FragmentDonateBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.common.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonateFragment : BaseFragment<FragmentDonateBinding>(R.layout.fragment_donate) {

    private val donateViewModel by activityViewModels<DonateViewModel>()
    private val campaignListAdapter by lazy { CampaignListAdapter(this::navigationToCampaignInfoFragment) }

    override fun initView() {
        observe()
        getCampaignList()
        initOption()
        initRecyclerView()
    }

    private fun initOption() {
        binding.cfcRecent.changeState(CustomFilterChip.State.DESC)
    }

    private fun initRecyclerView() {
        binding.rvCampaign.apply {
            campaignListAdapter.submitList(arrayListOf())
            adapter = campaignListAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            addItemDecoration(GridItemDecoration(requireContext(), 2, 20, 10))
        }
    }

    private fun observe() {
        donateViewModel.campaignList.observe(viewLifecycleOwner) {
            it?.let {
                campaignListAdapter.submitList(it)
            }
        }
    }

    private fun navigationToCampaignInfoFragment() {
        navigate(MainFragmentDirections.actionToCampaignInfoFragment())
    }

    private fun getCampaignList() {
        donateViewModel.getCampaignList("date", 1)
    }
}