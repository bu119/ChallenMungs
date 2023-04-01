package com.ssafy.challenmungs.presentation.home

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.data.local.datasource.SharedPreferences
import com.ssafy.challenmungs.databinding.FragmentHomeBinding
import com.ssafy.challenmungs.databinding.FragmentPanelCreateBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

@AndroidEntryPoint
class HomeFragment(): BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initView() {
        Log.d("gggg", "시작해요")
        val url = "http://j8d2101.p.ssafy.io:8080/wallet/tokenConfirm/totalDonate"
        HttpAsyncTask(url).execute()
    }
    class HttpAsyncTask(private val url: String) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYWVzZmFzZXBmamFzZWlnamFzcmdqc3JrbGR2a2xkZnZsZm1iZGZiZ21qc2Rpb2dqYmlvc2Ryamdpb2FzamdvcGFzZ2pzb3B2amlvc2RmbWp2c2Rpb3Zqc2lvanZzaW9kcmp2aW9zZHJ2amlvc2RyaiIsImlhdCI6MTY3OTgwMTU4MSwiZXhwIjoxNjgwNjY1NTgxLCJsb2dpbklkIjoib3BpNkBoYW5tYWlsLm5ldCJ9.igjQMAIEYEI9wK_Kzy-iBH0V-a3PemNhGhDt7LCmTGNbwJ-nnr7YgGrZ3JS4aw419EcaHmO5sxlGbkLkAoWPMQ")
                .build()

            val response = client.newCall(request).execute()

            val responseBody = response.body?.string()
            response.close()

            return responseBody ?: ""
        }

        override fun onPostExecute(result: String?) {
            // do something with the response body
            Log.d("gggg", result!!)
        }
    }
}

