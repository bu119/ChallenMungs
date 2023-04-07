package com.ssafy.challenmungs.presentation.mypage

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
class MyWalletFragment : BaseFragment<FragmentMyWalletBinding>(R.layout.fragment_my_wallet) {

    private val myPageViewModel by activityViewModels<MyPageViewModel>()
    private val myWalletViewModel by activityViewModels<MyWalletViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun initView() {
        initData()
        setBind()
        initListener()
        initRecyclerView()
    }

    private fun setBind() {
        binding.toolbar.title = getString(R.string.title_my_wallet)
        myPageViewModel.myWalletBalance.observe(viewLifecycleOwner) {
            binding.tvCurrentBalanceAmount.text = it
        }

        myWalletViewModel.walletHistory.observe(viewLifecycleOwner) {
            it?.let {
                binding.rvBankStatement.setItems(it)
            }
        }
    }

    private fun initData() {
        lifecycleScope.launch {
            myPageViewModel.getMyWalletBalance("w")
            myWalletViewModel.getMyWalletHistory()
        }
    }

    private fun initListener() {
        binding.toolbar.ivBack.setOnClickListener {
            popBackStack()
        }

        binding.tvAddressCopy.setOnClickListener {
            copyAddress()
        }

        binding.btnParticipation.setOnClickListener {
            popBackStack()
            // homeFragment의 status를 챌린지 탭으로 설정하는 코드 필요
            mainViewModel.setStatus(0)
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            rvBankStatement.apply {
                setItems(arrayListOf())
            }
        }
    }

    private fun copyAddress() {
        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("my wallet address", "Hello, World!")
        clipboard.setPrimaryClip(clip)
    }
}