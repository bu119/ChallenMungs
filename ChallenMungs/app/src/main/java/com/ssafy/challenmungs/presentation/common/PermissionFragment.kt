package com.ssafy.challenmungs.presentation.common

import android.content.Context
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.backDoublePressedFragmentCallback
import com.ssafy.challenmungs.databinding.FragmentPermissionBinding
import com.ssafy.challenmungs.presentation.auth.AuthActivity
import com.ssafy.challenmungs.presentation.auth.MemberViewModel
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : BaseFragment<FragmentPermissionBinding>(R.layout.fragment_permission) {

    private lateinit var callback: OnBackPressedCallback
    private val memberViewModel by activityViewModels<MemberViewModel>()

    override fun initView() {
        ApplicationClass.preferences.isFirstRun = false
        initListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = backDoublePressedFragmentCallback(this@PermissionFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }

    private fun initListener() {
        binding.btnPermissionCheck.setOnClickListener {
            if (memberViewModel.memberInfo.value != null) {
                if (memberViewModel.memberInfo.value!!.type == "n")
                    navigate(PermissionFragmentDirections.actionToMainFragment())
                else
                    navigate(PermissionFragmentDirections.actionToShelterHomeFragment())
            } else {
                val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}