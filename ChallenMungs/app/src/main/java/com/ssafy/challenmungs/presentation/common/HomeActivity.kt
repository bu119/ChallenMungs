package com.ssafy.challenmungs.presentation.common

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.px
import com.ssafy.challenmungs.common.util.setImmersiveMode
import com.ssafy.challenmungs.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var toast: Toast
    private lateinit var navController: NavController
    var backPressedTime: Long = 0
    private val menus = arrayOf("challenge", "donate", "home", "map", "my_page")
    private val menusNavigation = arrayOf(R.id.challenge_fragment, 0, 0, 0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setImmersiveMode()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_home) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.navigation_home)
        navController.graph = navGraph

        initView()
    }

    override fun finish() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            toast = Toast.makeText(this@HomeActivity, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT)
            toast.show()
            backPressedTime = System.currentTimeMillis()
        } else {
            super.finish()
            toast.cancel()
        }
    }

    private fun initView() {
        menus.forEachIndexed { tabIndex, tabName ->
            setMenu(tabName, tabIndex)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun setMenu(tabName: String, tabIndex: Int) {
        val tabId = resources.getIdentifier("tab_$tabName", "id", packageName)
        val imageId = resources.getIdentifier("ic_$tabName", "drawable", packageName)
        val stringId: Int = resources.getIdentifier("title_tab_$tabName", "string", packageName)
        val tab: LinearLayout = findViewById(tabId)
        val ivMenu: ImageView = tab.findViewById(R.id.iv_menu)
        val tvMenu: TextView = tab.findViewById(R.id.tv_menu)

        ivMenu.setImageDrawable(ContextCompat.getDrawable(this@HomeActivity, imageId))
        tvMenu.text = getText(stringId)

        tab.setOnClickListener {
            selected(tabName)
            navController.navigate(menusNavigation[tabIndex])
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
                ivMenu.layoutParams.height = 33.px(this@HomeActivity)
                ivMenu.layoutParams.width = 33.px(this@HomeActivity)
                ivMenu.requestLayout()

                tvMenu.setTypeface(null, Typeface.BOLD)
            } else {
                ivMenu.layoutParams.height = 22.px(this@HomeActivity)
                ivMenu.layoutParams.width = 22.px(this@HomeActivity)
                ivMenu.requestLayout()

                tvMenu.setTypeface(null, Typeface.NORMAL)
            }
        }
    }
}