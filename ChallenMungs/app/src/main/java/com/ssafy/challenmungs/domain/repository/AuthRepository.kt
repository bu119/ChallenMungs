package com.ssafy.challenmungs.domain.repository

import com.ssafy.challenmungs.data.remote.Resource
import com.ssafy.challenmungs.domain.entity.Auth
import okhttp3.RequestBody

interface AuthRepository {

    suspend fun requestLogin(body: RequestBody): Resource<Auth>
}