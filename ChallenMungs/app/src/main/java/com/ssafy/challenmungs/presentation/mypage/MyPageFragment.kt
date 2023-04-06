package com.ssafy.challenmungs.presentation.mypage

import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentMyPageBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.common.CustomSimpleDialog
import com.ssafy.challenmungs.presentation.common.CustomSimpleDialogInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page),
    CustomSimpleDialogInterface {

    override fun initView() {
        binding.tvLogout.setOnClickListener {
            val dialog = CustomSimpleDialog(
                requireContext(),
                this@MyPageFragment,
                false,
                "로그아웃 하시겠어요?",
                "확인"
            )
            dialog.show()
        }
    }

    override fun onPositiveButton() {
        ApplicationClass.preferences.clearPreferences()
        requireActivity().finish()
    }
}