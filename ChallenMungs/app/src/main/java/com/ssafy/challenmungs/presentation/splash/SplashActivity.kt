package com.ssafy.challenmungs.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.setImmersiveMode
import com.ssafy.challenmungs.presentation.PermissionActivity
import com.ssafy.challenmungs.presentation.auth.AuthActivity
import com.ssafy.challenmungs.presentation.auth.MemberViewModel
import com.ssafy.challenmungs.presentation.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val _splashViewTime: Long = 5000
    private val memberViewModel by viewModels<MemberViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setImmersiveMode()
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            var result = false

            if (ApplicationClass.preferences.accessToken != null)
                result = memberViewModel.getMemberInfo()

            val intent = when ("${ApplicationClass.preferences.isFirstRun}, $result") {
                "false, true" -> Intent(this@SplashActivity, MainActivity::class.java)
                "false, false" -> Intent(this@SplashActivity, AuthActivity::class.java)
                else -> Intent(this@SplashActivity, PermissionActivity::class.java)
            }

            delay(_splashViewTime)
            startActivity(intent)
            finish()
        }
    }
}