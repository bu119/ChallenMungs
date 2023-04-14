package com.ssafy.challenmungs.presentation.base

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.presentation.auth.AuthActivity
import kotlinx.coroutines.*

abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes val layoutRes: Int
) : Fragment() {

    private lateinit var _binding: T
    val binding: T
        get() = _binding

    private val navController: NavController
        get() = NavHostFragment.findNavController(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this@BaseFragment
        setEditTextFocus()

        initView()
    }

    abstract fun initView()

    fun navigate(directions: NavDirections) {
        navController.navigate(directions)
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun setEditTextFocus() {
        binding.root.apply {
            setOnClickListener {
                hideKeyboard()
                it.clearFocus()
            }

            addOnLayoutChangeListener { v, _, _, _, _, _, _, _, _ ->
                val keyboardHeight = getKeyboardHeight(v)
                if (keyboardHeight <= 0) {
                    setImmersive()
                    v.clearFocus()
                }
            }
        }
    }

    private fun getKeyboardHeight(view: View): Int {
        val rect = Rect()
        view.getWindowVisibleDisplayFrame(rect)
        val screenHeight = view.rootView.height
        return screenHeight - rect.bottom
    }

    fun navigationNavHostFragmentToDestinationFragment(
        destinationFragmentId: Int,
        selectedId: Long = 0
    ) {
        activity?.let {
            val fragmentContainerViewId =
                if (activity is AuthActivity) R.id.nav_auth else R.id.nav_main
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(fragmentContainerViewId) as NavHostFragment
            val bundle = Bundle()
            bundle.putLong("cardId", selectedId)
            navHostFragment.navController.navigate(destinationFragmentId, bundle)
        }
    }

    fun getSelectedId(): Long? = arguments?.getLong("cardId")

    @Suppress("DEPRECATION")
    @OptIn(DelicateCoroutinesApi::class)
    private fun setImmersive() {
        // API 30 이상인 경우에는 WindowInsetsController를 사용하여 Fullscreen 모드로 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = requireActivity().window.insetsController ?: return
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            GlobalScope.launch(Dispatchers.Main) {
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            }
        } else {
            // API 30 미만인 경우에는 deprecated 된 API를 사용하여 Fullscreen 모드로 설정
            val decorView = requireActivity().window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                    GlobalScope.launch(Dispatchers.Main) {
                        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                    }
                }
            }
        }
    }
}