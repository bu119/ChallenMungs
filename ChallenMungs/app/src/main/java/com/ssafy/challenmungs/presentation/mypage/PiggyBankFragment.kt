package com.ssafy.challenmungs.presentation.mypage

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.BindingAdapters.setItems
import com.ssafy.challenmungs.databinding.FragmentMyWalletBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.common.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PiggyBankFragment : BaseFragment<FragmentMyWalletBinding>(R.layout.fragment_my_wallet) {

    private val myPageViewModel by activityViewModels<MyPageViewModel>()
    private val piggyBankViewModel by activityViewModels<PiggyBankViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun initView() {
        initData()
        setBind()
        initListener()
        initRecyclerView()
    }

    private fun initListener() {
        binding.toolbar.ivBack.setOnClickListener {
            popBackStack()
        }

        binding.btnParticipation.setOnClickListener {
            popBackStack()
            // homeFragment의 status를 기부 탭으로 설정하는 코드 필요
            mainViewModel.setStatus(1)
        }
    }

    private fun setBind() {
        binding.apply {
            tvAddressCopy.visibility = View.GONE
            toolbar.title = getString(R.string.title_donate_bank)
            tvCurrentBalanceTitle.text = getString(R.string.content_donation_possible_amount)
            btnParticipation.text = getString(R.string.content_donate)
        }
        myPageViewModel.piggyBankBalance.observe(viewLifecycleOwner) {
            binding.tvCurrentBalanceAmount.text = it
        }
        piggyBankViewModel.piggyBankHistory.observe(viewLifecycleOwner) {
            it?.let {
                binding.rvBankStatement.setItems(it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            rvBankStatement.apply {
                setItems(arrayListOf())
            }
        }
    }

    private fun initData() {
        lifecycleScope.launch {
            myPageViewModel.getMyWalletBalance("p")
            piggyBankViewModel.getMyWalletHistory()
        }
    }
}