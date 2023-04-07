package com.ssafy.challenmungs.presentation.auth

import android.content.Context
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.backDoublePressedFragmentCallback
import com.ssafy.challenmungs.databinding.FragmentLogInBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(R.layout.fragment_log_in) {

    private lateinit var pressedCallback: OnBackPressedCallback
    private val TAG = "KaKao-Login"
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val memberViewModel by activityViewModels<MemberViewModel>()
    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            authViewModel.setAccessToken(token.accessToken)
        }
    }

    override fun initView() {
        initListener()
        observeAccessToken()
        observeFlag()
        observeMemberInfo()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pressedCallback = backDoublePressedFragmentCallback(this@LogInFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        pressedCallback.remove()
    }

    private fun initListener() {
        binding.btnKakaoLogin.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                // 카카오톡으로 로그인
                UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error)

                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled)
                            return@loginWithKakaoTalk

                        UserApiClient.instance.loginWithKakaoAccount(
                            requireContext(),
                            callback = callback
                        )
                    } else if (token != null) {
                        Log.i(TAG, "로그인 성공 ${token.accessToken}")
                        authViewModel.setAccessToken(token.accessToken)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
            }
        }

        binding.tvContentShelterJoin.setOnClickListener {
            navigate(LogInFragmentDirections.actionToShelterInviteCodeFragment())
        }
    }

    private fun observeAccessToken() {
        authViewModel.accessToken.observe(viewLifecycleOwner) {
            if (it != null)
                authViewModel.requestLogin(it.toRequestBody("text/plain".toMediaTypeOrNull()))
        }
    }

    private fun observeFlag() {
        authViewModel.authType.observe(viewLifecycleOwner) {
            when (it) {
                "new" -> navigate(LogInFragmentDirections.actionToOnBoardingFragment())
                "member" ->
                    if (ApplicationClass.preferences.accessToken != null) {
                        lifecycleScope.launch {
                            memberViewModel.getMemberInfo()
                        }
                    }
            }
        }
    }

    private fun observeMemberInfo() {
        memberViewModel.memberInfo.observe(viewLifecycleOwner) {
            if (it != null)
                navigate(LogInFragmentDirections.actionToMainFragment())
        }
    }
}