package com.ssafy.challenmungs.common.util

import com.ssafy.challenmungs.data.remote.Resource
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> wrapToResource(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(apiCall())
    } catch (throwable: Throwable) {
        when(throwable) {
            is IOException -> Resource.Error(throwable.message ?: "ERROR1")
            is HttpException -> {
                val code = throwable.code()
                Resource.Error(code.toString())
            }
            else -> {
                Resource.Error(throwable.message ?: "ERROR2")
            }
        }
    }
}