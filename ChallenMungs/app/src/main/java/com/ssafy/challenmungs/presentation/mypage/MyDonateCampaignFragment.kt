package com.ssafy.challenmungs.presentation.mypage

import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.BaseListBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment

class MyDonateCampaignFragment : BaseFragment<BaseListBinding>(R.layout.base_list) {

    override fun initView() {
        setBind()
        initListener()
    }

    private fun setBind() {
        binding.toolbar.title = getString(R.string.title_my_campaign)
    }

    private fun initListener() {
        binding.toolbar.ivBack.setOnClickListener {
            popBackStack()
        }
    }
}