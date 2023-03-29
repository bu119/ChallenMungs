package com.ssafy.challenmungs.presentation.home

import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentHomeBinding
import com.ssafy.challenmungs.databinding.FragmentPanelCreateBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import java.text.DecimalFormat

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val TAG = "Home-Fragment"

    override fun initView() {
        loadMyTotalDonation()
    }

    fun loadMyTotalDonation() {
        var myTotalDonation: Int;
        myTotalDonation = 12345;
        val decimalFormat = DecimalFormat("#,###")
        val formattedNumber: String = decimalFormat.format(myTotalDonation)
        binding.myTotalDonation.text = formattedNumber
    }
}