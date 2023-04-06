package com.ssafy.challenmungs.presentation.donate

import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentCampaignInfoBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignInfoFragment :
    BaseFragment<FragmentCampaignInfoBinding>(R.layout.fragment_campaign_info) {

    private val donateViewModel by activityViewModels<DonateViewModel>()

    override fun initView() {
        initListener()
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener {
            popBackStack()
        }

        binding.llDonate.setOnClickListener {
            donateViewModel.getBalance("p")
            navigationNavHostFragmentToDestinationFragment(R.id.campaign_donate_fragment)
        }
    }
}