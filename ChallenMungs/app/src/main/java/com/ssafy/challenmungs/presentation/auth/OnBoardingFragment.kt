package com.ssafy.challenmungs.presentation.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentOnBoardingBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.home.HomeActivity
import com.ssafy.challenmungs.presentation.klaytn.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    private val authViewModel by activityViewModels<AuthViewModel>()
    private val memberViewModel by activityViewModels<MemberViewModel>()
    private val walletViewModel by activityViewModels<WalletViewModel>()
    private val callback = focus()

    override fun initView() {
        binding.toolbar.tvTitle.text = getString(R.string.title_member_onboarding)
        initListener()
        observe()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            if (authViewModel.accessToken.value != null) {
                authViewModel.requestJoin(binding.tilEtNickname.text.toString())
            }
        }

        binding.toolbar.ivBack.setOnClickListener {
            popBackStack()
        }

        binding.root.setOnClickListener {
            hideKeyboard()
            it.clearFocus()
        }
    }

    private fun observe() {
        authViewModel.authType.observe(viewLifecycleOwner) {
            if (it == "member") {
                walletViewModel.createAccount()
                walletViewModel.createAccount()
            }
        }

        walletViewModel.address.observe(viewLifecycleOwner) {
            if (it.size == 2)
                lifecycleScope.launch {
                    memberViewModel.getMemberInfo()
                }
        }

        memberViewModel.memberInfo.observe(viewLifecycleOwner) {
            if (it != null)
                lifecycleScope.launch {
                    val result = authViewModel.setWallet(
                        it.memberId,
                        walletViewModel.address.value!![0],
                        walletViewModel.address.value!![1]
                    )

                    if (result) {
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
        }
    }

    private fun focus(): OnBackPressedCallback {

        return object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                Log.d(
                    "OnBoardingFragment",
                    "OnBoardingFragment: focused(${binding.tilEtNickname.isFocused})"
                )

                if (binding.tilEtNickname.isFocused) {
                    hideKeyboard()
//                    binding.root.clearFocus()
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}