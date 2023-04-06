package com.ssafy.challenmungs.presentation.shelter

import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentShelterHomeBinding
import com.ssafy.challenmungs.presentation.auth.MemberViewModel
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.common.CustomSimpleDialog
import com.ssafy.challenmungs.presentation.common.CustomSimpleDialogInterface

class ShelterHomeFragment :
    BaseFragment<FragmentShelterHomeBinding>(R.layout.fragment_shelter_home),
    CustomSimpleDialogInterface {

    private val memberViewModel by activityViewModels<MemberViewModel>()

    override fun initView() {
        memberViewModel.memberInfo.value?.let {
            binding.member = it
        }

        binding.tvLogout.setOnClickListener {
            val dialog = CustomSimpleDialog(
                requireContext(),
                this@ShelterHomeFragment,
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