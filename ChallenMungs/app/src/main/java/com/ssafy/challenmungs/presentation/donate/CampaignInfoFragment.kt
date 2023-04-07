package com.ssafy.challenmungs.presentation.donate

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.LinearItemDecoration
import com.ssafy.challenmungs.databinding.FragmentCampaignInfoBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignInfoFragment :
    BaseFragment<FragmentCampaignInfoBinding>(R.layout.fragment_campaign_info) {

    private val donateViewModel by activityViewModels<DonateViewModel>()
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            donateViewModel.initCampaignInfo()
            popBackStack()
        }
    }

    override fun initView() {
        donateViewModel.campaignInfo.value?.let {
            binding.data = it
        }
        initListener()
        initRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.onBackPressedDispatcher?.addCallback(this@CampaignInfoFragment, callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener {
            donateViewModel.initCampaignInfo()
            popBackStack()
        }

        binding.llDonate.setOnClickListener {
            donateViewModel.getBalance("p")
            navigationNavHostFragmentToDestinationFragment(R.id.campaign_donate_fragment)
        }
    }

    private fun initRecyclerView() {
        binding.rvContent.apply {
            donateViewModel.campaignInfo.value?.let {
                adapter = CampaignContentAdapter(it.contentList)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                addItemDecoration(
                    LinearItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        15
                    )
                )
            }
        }
    }
}