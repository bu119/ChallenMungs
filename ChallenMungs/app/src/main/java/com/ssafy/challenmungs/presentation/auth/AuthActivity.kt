package com.ssafy.challenmungs.presentation.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.setImmersiveMode
import com.ssafy.challenmungs.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val memberViewModel by viewModels<MemberViewModel>()
    private var startDestination: Int = R.id.log_in_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ChallenMungs)
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setImmersiveMode()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.navigation_auth)

        if (ApplicationClass.preferences.accessToken != null)
            lifecycleScope.launch {
                val success = memberViewModel.getMemberInfo()
                if (success)
                    startDestination = R.id.main_activity
            }

        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
    }
}