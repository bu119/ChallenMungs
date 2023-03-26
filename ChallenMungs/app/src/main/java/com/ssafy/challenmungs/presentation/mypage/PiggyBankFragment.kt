package com.ssafy.challenmungs.presentation.mypage

import android.view.View
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentMyWalletBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment

class PiggyBankFragment : BaseFragment<FragmentMyWalletBinding>(R.layout.fragment_my_wallet) {

    override fun initView() {
        binding.apply {
            tvAddressCopy.visibility = View.GONE
            toolbar.tvTitle.text = getString(R.string.title_donate_bank)
            tvCurrentBalanceTitle.text = getString(R.string.content_donation_possible_amount)
            btnParticipation.text = getString(R.string.content_donate)
        }
    }

}