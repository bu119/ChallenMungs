package com.ssafy.challenmungs.presentation.auth

import android.util.Log
import androidx.fragment.app.viewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentLogInBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(R.layout.fragment_log_in) {

    private val TAG = "KaKao-Login"
    private val authViewModel by viewModels<AuthViewModel>()
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
                    } else if (token != null) Log.i(TAG, "로그인 성공 ${token.accessToken}")
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
            }
        }
    }

    private fun observeAccessToken() {
        authViewModel.accessToken.observe(viewLifecycleOwner) {
            if (it != null)
                authViewModel.requestLogin(it.toRequestBody("text/plain".toMediaTypeOrNull()))
        }
    }
}