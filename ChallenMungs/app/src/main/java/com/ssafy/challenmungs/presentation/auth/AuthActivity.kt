package com.ssafy.challenmungs.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.setImmersiveMode
import com.ssafy.challenmungs.databinding.ActivityAuthBinding
import com.ssafy.challenmungs.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val memberViewModel by viewModels<MemberViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ChallenMungs)
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setImmersiveMode()

        observeMemberInfo()
        if (ApplicationClass.preferences.accessToken != null)
            checkAccessToken()
    }

    private fun checkAccessToken() {
        memberViewModel.getMemberInfo()
    }

    private fun observeMemberInfo() {
        memberViewModel.memberInfo.observe(this) {
            val intent = Intent(this@AuthActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}