package com.ssafy.challenmungs.presentation.auth

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentShelterInviteCodeBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment

class ShelterInviteCodeFragment :
    BaseFragment<FragmentShelterInviteCodeBinding>(R.layout.fragment_shelter_invite_code) {

    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun initView() {
        binding.apply {
            btnSave.setOnClickListener {
                val enable =
                    tilEtShelterName.text.toString() != "" && tilEtEmail.text.toString() != ""

                if (enable) {
                    authViewModel.requestInviteCode(
                        tilEtShelterName.text.toString(),
                        tilEtEmail.text.toString()
                    )
                    navigate(ShelterInviteCodeFragmentDirections.actionToShelterJoinFragment())
                } else Toast.makeText(requireContext(), "정보를 바르게 입력해주세요", Toast.LENGTH_SHORT)
                    .show()
            }

            tvAlreadyExist.setOnClickListener {
                navigate(ShelterInviteCodeFragmentDirections.actionToShelterJoinFragment())
            }
        }
    }
}