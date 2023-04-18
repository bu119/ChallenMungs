package com.ssafy.challenmungs.presentation.mypage

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.BindingAdapters.setItems
import com.ssafy.challenmungs.databinding.FragmentDonationDetailsBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class DonationHistoryFragment :
    BaseFragment<FragmentDonationDetailsBinding>(R.layout.fragment_donation_details) {

    private val donationHistoryViewModel by activityViewModels<DonationHistoryViewModel>()
    private var year = 0

    override fun initView() {
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]

        donationHistoryViewModel.setSelectedYear(year)

        initData()
        setBind()
        initListener()
        initRecyclerView()
    }

    private fun setBind() {
        binding.apply {
            toolbar.title = getString(R.string.title_donate_history)
            donationHistoryViewModel.selectedYear.observe(viewLifecycleOwner) {
                tvYear.text = it.toString()
                initData()
            }

            btnYearLeft.setOnClickListener {
                year -= 1
                donationHistoryViewModel.setSelectedYear(year)
            }

            btnYearRight.setOnClickListener {
                year += 1
                donationHistoryViewModel.setSelectedYear(year)
            }

        }

        donationHistoryViewModel.donationHistory.observe(viewLifecycleOwner) {
            it?.let {
                binding.rvDonationStatement.setItems(it)
            }
        }

        donationHistoryViewModel.donationSummary.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvDonationNumberAmount.text = it.donateCount.toString()
                binding.tvTotalityAmount.text = it.sumYearMoney.toString()
                binding.tvTotalContributionAmount.text = it.sumTotalMoney.toString()
            }
        }
    }

    private fun initListener() {
        binding.toolbar.ivBack.setOnClickListener {
            popBackStack()
        }
    }

    private fun initData() {
        lifecycleScope.launch {
            donationHistoryViewModel.apply {
                getDonationSummary()
                getDonationHistory()
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            rvDonationStatement.apply {
                setItems(arrayListOf())
            }
        }
    }
}