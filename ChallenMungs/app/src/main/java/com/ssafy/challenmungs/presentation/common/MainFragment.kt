package com.ssafy.challenmungs.presentation.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.backDoublePressedFragmentCallback
import com.ssafy.challenmungs.common.util.px
import com.ssafy.challenmungs.databinding.FragmentMainBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private lateinit var navController: NavController
    private lateinit var callback: OnBackPressedCallback
    private val menus = arrayOf("challenge", "donate", "home", "map", "my_page")
    private val menusNavigation = arrayOf(
        R.id.challenge_fragment,
        R.id.donate_fragment,
        R.id.home_fragment,
        R.id.information_fragment,
        R.id.my_page_fragment
    )
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun initView() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_home) as NavHostFragment
        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.navigation_home)
        navController.graph = navGraph

        mainViewModel.status.observe(viewLifecycleOwner) {
            navController.navigate(menusNavigation[it])
            selected(menus[it])
        }

        menus.forEachIndexed { tabIndex, tabName ->
            setMenu(tabName, tabIndex)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = backDoublePressedFragmentCallback(this@MainFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }

    @SuppressLint("DiscouragedApi")
    private fun setMenu(tabName: String, tabIndex: Int) {
        val tabId = resources.getIdentifier("tab_$tabName", "id", requireActivity().packageName)
        val imageId =
            resources.getIdentifier("ic_$tabName", "drawable", requireActivity().packageName)
        val stringId: Int =
            resources.getIdentifier("title_tab_$tabName", "string", requireActivity().packageName)
        val tab: LinearLayout = requireActivity().findViewById(tabId)
        val ivMenu: ImageView = tab.findViewById(R.id.iv_menu)
        val tvMenu: TextView = tab.findViewById(R.id.tv_menu)

        ivMenu.setImageDrawable(ContextCompat.getDrawable(requireContext(), imageId))
        tvMenu.text = getText(stringId)

        tab.setOnClickListener {
            selected(tabName)
            mainViewModel.setStatus(tabIndex)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun selected(tabName: String) {
        for (menu in menus) {
            val tabId = resources.getIdentifier("tab_$menu", "id", requireActivity().packageName)
            val tab: LinearLayout = requireActivity().findViewById(tabId)
            val ivMenu: ImageView = tab.findViewById(R.id.iv_menu)
            val tvMenu: TextView = tab.findViewById(R.id.tv_menu)

            if (menu == tabName) {
                ivMenu.layoutParams.height = 33.px(requireContext())
                ivMenu.layoutParams.width = 33.px(requireContext())
                ivMenu.requestLayout()

                tvMenu.setTypeface(null, Typeface.BOLD)
            } else {
                ivMenu.layoutParams.height = 22.px(requireContext())
                ivMenu.layoutParams.width = 22.px(requireContext())
                ivMenu.requestLayout()

                tvMenu.setTypeface(null, Typeface.NORMAL)
            }
        }
    }
}