package com.ssafy.challenmungs.presentation.donate

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentCampaignDonateBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class CampaignDonateFragment :
    BaseFragment<FragmentCampaignDonateBinding>(R.layout.fragment_campaign_donate) {

    private val donateViewModel by activityViewModels<DonateViewModel>()

    override fun initView() {
        binding.vm = donateViewModel

        donateViewModel.campaignInfo.value?.let {
            binding.tvShelterName.text = it.shelterName
        }

        initListener()
    }

    private fun initListener() {
        var result = ""
        val decimalFormat = DecimalFormat("#,###")

        with(binding) {
            etDonateAmount.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!TextUtils.isEmpty(p0) && p0.toString() != result) {
                        result = decimalFormat.format((p0.toString().replace(",", "")).toDouble())
                        etDonateAmount.setText(result)
                        etDonateAmount.setSelection(result.length)
                    }

                    btnDonate.isEnabled = !TextUtils.isEmpty(p0)
                            && !TextUtils.isEmpty(tilEtComment.text)
                            && p0.toString().toInt() != 0
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            tilEtComment.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    btnDonate.isEnabled = !TextUtils.isEmpty(p0)
                            && !TextUtils.isEmpty(etDonateAmount.text)
                            && etDonateAmount.text.toString().toInt() != 0
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            llDonateAll.setOnClickListener {
                donateViewModel.balancePiggyBank.value?.let {
                    result = decimalFormat.format((it.toString().replace(",", "")).toDouble())
                    etDonateAmount.setText(result)
                    etDonateAmount.setSelection(result.length)
                    btnDonate.isEnabled = !TextUtils.isEmpty(tilEtComment.text)
                            && etDonateAmount.text.toString().toInt() != 0
                }
            }

            btnDonate.setOnClickListener {
                donateViewModel.selectedCampaignId.value?.let {
                    donateViewModel.requestDonate(
                        it,
                        etDonateAmount.text.toString().toInt(),
                        tilEtComment.text.toString()
                    )
                }
                popBackStack()
            }
        }
    }
}