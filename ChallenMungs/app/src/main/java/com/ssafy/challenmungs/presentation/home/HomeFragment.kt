package com.ssafy.challenmungs.presentation.home

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentHomeBinding
import com.ssafy.challenmungs.databinding.ItemChallengeCardOngoingFinishBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.mypage.MyChallengeListAdapter
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat


@AndroidEntryPoint
class HomeFragment(): BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initView() {
        // BASEURL 어떻게 쓰나요
        val jwt = ApplicationClass.preferences.accessToken.toString()
        GetTotalDonation("http://j8d2101.p.ssafy.io:8080/wallet/tokenConfirm/totalDonate", jwt, binding).execute()
        GetMyOngoingChallengeList("http://j8d2101.p.ssafy.io:8080/challenge/tokenConfirm/getList", jwt, binding, requireContext()).execute()
        
    }
    class GetTotalDonation(private val url: String, private val token: String, private val binding: FragmentHomeBinding) : AsyncTask<Void, Void, String>() {
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
    class GetMyOngoingChallengeList(private val url: String, private val token: String, private val binding: FragmentHomeBinding, private val context: Context): AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String {
            val requestBody = FormBody.Builder()
                .add("type", "1")
                .add("myChallenge", "true")
                .add("onlyTomorrow", "false")
                .build()
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .header("Authorization", token)
                .post(requestBody)
                .build()
            val response = client.newCall(request).execute()

            val responseBody = response.body?.string()
            response.close()

            return responseBody ?: ""
        }

        override fun onPostExecute(result: String?) {
            val jsonArray = JSONObject(result).getJSONArray("1")
            val list = mutableListOf<Map<String, Any>>()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val map = mutableMapOf<String, Any>()
                val keys = jsonObj.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val value = jsonObj.get(key)
                    map.put(key, value)
                }
                list.add(map)
            }
            Log.d("gggg", "::${list}")
//            val myRecyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
            val rv = binding.rvOngoing
            val adapter = MyChallengeListAdapter(list)
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(context)
        }
    }
}

