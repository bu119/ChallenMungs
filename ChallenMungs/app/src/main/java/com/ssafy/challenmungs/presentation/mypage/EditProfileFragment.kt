package com.ssafy.challenmungs.presentation.mypage

import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.BindingAdapters.setProfileImg
import com.ssafy.challenmungs.databinding.FragmentEditProfileBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val editProfileViewModel by activityViewModels<EditProfileViewModel>()

    override fun initView() {
        binding.vm = editProfileViewModel
        setData()
        initListener()
    }

    private fun setData() {
        binding.toolbar.title = getString(R.string.title_edit_profile)

        editProfileViewModel.profileImgUrl.observe(viewLifecycleOwner) {
            binding.ivProfile.setProfileImg(it)
        }
    }

    private fun initListener() {
        binding.toolbar.ivBack.setOnClickListener {
            popBackStack()
        }
    }
}