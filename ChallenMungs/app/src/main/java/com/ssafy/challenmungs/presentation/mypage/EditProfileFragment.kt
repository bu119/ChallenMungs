package com.ssafy.challenmungs.presentation.mypage

import com.bumptech.glide.Glide
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentEditProfileBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment

class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    override fun initView() {
        val toolbar = binding.toolbar
        toolbar.tvTitle.text = getString(R.string.title_edit_profile)

        val profileImage = binding.ivProfile
        Glide.with(this).load(R.drawable.ic_profile_default).circleCrop().into(profileImage)
    }
}