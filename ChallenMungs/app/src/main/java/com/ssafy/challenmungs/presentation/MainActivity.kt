package com.ssafy.challenmungs.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.challenmungs.common.util.setImmersiveMode
import com.ssafy.challenmungs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setImmersiveMode()
    }
}