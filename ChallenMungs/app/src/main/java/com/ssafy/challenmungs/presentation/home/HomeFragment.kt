package com.ssafy.challenmungs.presentation.home

import android.content.Context
import android.os.AsyncTask
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.challenmungs.ApplicationClass
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentHomeBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.challenge.ChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat

@AndroidEntryPoint
class HomeFragment() : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    lateinit var myOngoingChallengeCardList: String
    lateinit var myChallengeOnlyTomorrowCardList: String
    lateinit var recentlyAddedCampaignCardList: String
    private val challengeViewModel by activityViewModels<ChallengeViewModel>()

    override fun initView() {
        val jwt = ApplicationClass.preferences.accessToken.toString()
        GetTotalDonation(
            "http://j8d2101.p.ssafy.io:8080/wallet/tokenConfirm/totalDonate",
            jwt,
            binding
        ).execute()

        GetMyOngoingChallengeList(
            "http://j8d2101.p.ssafy.io:8080/challenge/tokenConfirm/getList",
            jwt,
            binding,
            requireContext()
        ).execute()

        GetMyChallengeOnlyTomorrow(
            "http://j8d2101.p.ssafy.io:8080/challenge/tokenConfirm/getList",
            jwt,
            binding,
            requireContext()
        ).execute()

        GetRecentlyAddedCampaign(
            "http://j8d2101.p.ssafy.io:8080/campaign/list/ongoing",
            binding,
            requireContext()
        ).execute()

        binding.tvShowTotalButton.setOnClickListener {
            navigate(
                HomeFragmentDirections.actionToMyOngoingChallengeFragment(
                    myOngoingChallengeCardList
                )
            )
        }

        binding.tvShowMore.setOnClickListener {
            navigate(
                HomeFragmentDirections.actionToMyChallengeOnlyTomorrowFullFragment(
                    myChallengeOnlyTomorrowCardList
                )
            )
        }

        binding.tvShowMoreForRecent.setOnClickListener {
            navigate(
                HomeFragmentDirections.actionToRecentlyAddedCampaignFullFragment(
                    recentlyAddedCampaignCardList
                )
            )
        }

        observe()
    }

    private fun observe() {
        challengeViewModel.basicTodayList.observe(viewLifecycleOwner) {
            it?.let {
                navigationNavHostFragmentToDestinationFragment(
                    R.id.challenge_basic_fragment
                )
            }
        }
    }

    inner class GetTotalDonation(
        private val url: String,
        private val token: String,
        private val binding: FragmentHomeBinding
    ) : AsyncTask<Void, Void, String>() {

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
            val formattedNumber: String =
                decimalFormat.format(jsonObject.getString("result").toInt())

            binding.tvMyTotalDonation.text = formattedNumber
        }
    }

    inner class GetMyOngoingChallengeList(
        private val url: String,
        private val token: String,
        private val binding: FragmentHomeBinding,
        private val context: Context
    ) : AsyncTask<Void, Void, String>() {

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
            myOngoingChallengeCardList = JSONObject(responseBody).getJSONArray("1").toString() ?: ""
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
            val rv = binding.rvOngoing
            val adapter =
                MyChallengeListAdapter(
                    this@HomeFragment::navigationNavHostFragmentToDestinationFragment,
                    list,
                    challengeViewModel
                )
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    inner class GetMyChallengeOnlyTomorrow(
        private val url: String,
        private val token: String,
        private val binding: FragmentHomeBinding,
        private val context: Context
    ) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String {
            val requestBody = FormBody.Builder()
                .add("type", "1")
                .add("myChallenge", "true")
                .add("onlyTomorrow", "true")
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
            myChallengeOnlyTomorrowCardList =
                JSONObject(responseBody).getJSONArray("0").toString() ?: ""
            return responseBody ?: ""
        }

        override fun onPostExecute(result: String?) {
            val jsonArray = JSONObject(result).getJSONArray("0")
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
            val rv = binding.rvOnlyTomorrow
            rv.adapter = MyChallengeOnlyTomorrowAdapter(list)
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    inner class GetRecentlyAddedCampaign(
        private val url: String,
        private val binding: FragmentHomeBinding,
        private val context: Context
    ) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            response.close()
            recentlyAddedCampaignCardList = responseBody ?: ""
            return responseBody ?: ""
        }

        override fun onPostExecute(result: String?) {
            val jsonArray = JSONArray(result)
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
            val rv = binding.rvRecentlyAddedCampaign

            rv.adapter = RecentlyAddedCampaignAdpater(list)
            rv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}