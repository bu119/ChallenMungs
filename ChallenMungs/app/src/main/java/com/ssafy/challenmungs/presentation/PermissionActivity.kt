package com.ssafy.challenmungs.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }
}