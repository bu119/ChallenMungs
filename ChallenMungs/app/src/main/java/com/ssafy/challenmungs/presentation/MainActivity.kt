package com.ssafy.challenmungs.presentation

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.px
import com.ssafy.challenmungs.common.util.setImmersiveMode
import com.ssafy.challenmungs.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val menus = arrayOf("challenge", "donate", "home", "map", "my_page")

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ChallenMungs)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setImmersiveMode()
        initView()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navGraph =
            navHostFragment.navController
                .navInflater
                .inflate(R.navigation.navigation_main)
    }

    private fun initView() {
        for (menu in menus) {
            setMenu(menu)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun setMenu(tabName: String) {
        val tabId = resources.getIdentifier("tab_$tabName", "id", packageName)
        val imageId = resources.getIdentifier("ic_$tabName", "drawable", packageName)
        val stringId: Int = resources.getIdentifier("title_tab_$tabName", "string", packageName)
        val tab: LinearLayout = findViewById(tabId)
        val ivMenu: ImageView = tab.findViewById(R.id.iv_menu)
        val tvMenu: TextView = tab.findViewById(R.id.tv_menu)

        ivMenu.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, imageId))
        tvMenu.text = getText(stringId)

        tab.setOnClickListener {
            selected(tabName)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun selected(tabName: String) {
        for (menu in menus) {
            val tabId = resources.getIdentifier("tab_$menu", "id", packageName)
            val tab: LinearLayout = findViewById(tabId)
            val ivMenu: ImageView = tab.findViewById(R.id.iv_menu)
            val tvMenu: TextView = tab.findViewById(R.id.tv_menu)

            if (menu == tabName) {
                ivMenu.layoutParams.height = 33.px(this@MainActivity)
                ivMenu.layoutParams.width = 33.px(this@MainActivity)
                ivMenu.requestLayout()

                tvMenu.setTypeface(null, Typeface.BOLD)
            } else {
                ivMenu.layoutParams.height = 22.px(this@MainActivity)
                ivMenu.layoutParams.width = 22.px(this@MainActivity)
                ivMenu.requestLayout()

                tvMenu.setTypeface(null, Typeface.NORMAL)
            }
        }
    }
}