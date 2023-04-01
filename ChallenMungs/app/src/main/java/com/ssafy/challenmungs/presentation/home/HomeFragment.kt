package com.ssafy.challenmungs.presentation.home

import android.os.AsyncTask
import android.util.Log
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentHomeBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.text.DecimalFormat


@AndroidEntryPoint
class HomeFragment(): BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initView() {
        // BASEURL 어떻게 쓰나요
        HttpAsyncTask("http://j8d2101.p.ssafy.io:8080/wallet/tokenConfirm/totalDonate", ApplicationClass.preferences.accessToken.toString(), binding).execute()

    }
    class HttpAsyncTask(private val url: String, private val token: String, private val binding: FragmentHomeBinding) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .header("Authorization", token)
                .build()

            val response = client.newCall(request).execute()

            val responseBody = response.body?.string()
            response.close()

            return responseBody ?: ""
        }

        override fun onPostExecute(result: String?) {
            val jsonObject = JSONObject(result)
            val decimalFormat = DecimalFormat("#,###")
            val formattedNumber: String = decimalFormat.format(jsonObject.getString("result").toInt())
            binding.tvMyTotalDonation.text = formattedNumber
        }
    }
}

