package com.ssafy.challenmungs.presentation.common

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentPermissionBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : BaseFragment<FragmentPermissionBinding>(R.layout.fragment_permission) {

    override fun initView() {
        ApplicationClass.preferences.isFirstRun = false

        initListener()
    }

    private fun initListener() {
        binding.btnPermissionCheck.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // 로그인 체크 후 화면 분기
                }
//                shouldShowRequestPermissionRationale()
            }

            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {

                }
            }

            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED -> {

                }
            }
        }
    }
}