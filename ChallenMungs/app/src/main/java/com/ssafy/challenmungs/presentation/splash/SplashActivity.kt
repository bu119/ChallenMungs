package com.ssafy.challenmungs.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.common.util.setImmersiveMode
import com.ssafy.challenmungs.presentation.MainActivity
import com.ssafy.challenmungs.presentation.auth.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SPLASH_VIEW_TIME: Long = 5000

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val memberViewModel by viewModels<MemberViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setImmersiveMode()

        lifecycleScope.launch {
            if (ApplicationClass.preferences.accessToken != null)
                memberViewModel.getMemberInfo()

            delay(SPLASH_VIEW_TIME)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}