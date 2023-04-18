package com.ssafy.challenmungs.presentation.auth

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentShelterJoinBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShelterJoinFragment :
    BaseFragment<FragmentShelterJoinBinding>(R.layout.fragment_shelter_join) {

    private val authViewModel by activityViewModels<AuthViewModel>()
    private val memberViewModel by activityViewModels<MemberViewModel>()

    override fun initView() {
        observe()

        binding.apply {
            btnSave.setOnClickListener {
                val enable = tilEtMemberId.text.toString() != ""
                        && tilEtPassword.text.toString() != ""
                        && tilEtPasswordCheck.text.toString() != ""
                        && tilEtShelterName.text.toString() != ""
                        && tilEtInviteCode.text.toString() != ""
                        && tilEtPassword.text.toString() == tilEtPasswordCheck.text.toString()

                lifecycleScope.launch {
                    if (enable) {
                        val result = authViewModel.requestShelterJoin(
                            tilEtShelterName.text.toString(),
                            tilEtInviteCode.text.toString(),
                            tilEtMemberId.text.toString(),
                            tilEtPassword.text.toString()
                        )

                        if (result)
                            memberViewModel.getMemberInfo()
                    } else Toast.makeText(requireContext(), "정보를 바르게 입력해주세요", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun observe() {
        memberViewModel.memberInfo.observe(viewLifecycleOwner) {
            it?.let {
                navigate(ShelterJoinFragmentDirections.actionToShelterHomeFragment())
            }
        }
    }
}