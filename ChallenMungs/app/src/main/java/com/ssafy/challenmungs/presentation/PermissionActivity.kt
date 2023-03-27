package com.ssafy.challenmungs.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.common.util.setImmersiveMode
import com.ssafy.challenmungs.databinding.ActivityPermissionBinding

class PermissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setImmersiveMode()
        ApplicationClass.preferences.isFirstRun = false

        initListener()
    }

    private fun initListener() {
        binding.btnPermissionCheck.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this@PermissionActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // 로그인 체크 후 화면 분기
                }
//                shouldShowRequestPermissionRationale()
            }

            when {
                ContextCompat.checkSelfPermission(
                    this@PermissionActivity,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {

                }
            }

            when {
                ContextCompat.checkSelfPermission(
                    this@PermissionActivity,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED -> {

                }
            }
        }
    }
}