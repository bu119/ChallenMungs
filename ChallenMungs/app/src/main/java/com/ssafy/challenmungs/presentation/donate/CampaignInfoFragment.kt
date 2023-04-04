package com.ssafy.challenmungs.presentation.donate

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.backPressedCallbackInFullScreen
import com.ssafy.challenmungs.databinding.FragmentCampaignInfoBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.common.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignInfoFragment :
    BaseFragment<FragmentCampaignInfoBinding>(R.layout.fragment_campaign_info) {

    private lateinit var callback: OnBackPressedCallback
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun initView() {
        initListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = backPressedCallbackInFullScreen(
            this@CampaignInfoFragment,
            mainViewModel,
            this::popBackStack
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener {

        }
    }
}