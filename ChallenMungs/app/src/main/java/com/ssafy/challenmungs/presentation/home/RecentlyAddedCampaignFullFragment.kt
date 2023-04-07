package com.ssafy.challenmungs.presentation.home

import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentRecentlyAddedCampaignFullBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.information.InformationPlaceListFragmentArgs
import org.json.JSONArray

class RecentlyAddedCampaignFullFragment : BaseFragment<FragmentRecentlyAddedCampaignFullBinding>(R.layout.fragment_recently_added_campaign_full) {

    override fun initView() {
        val args: InformationPlaceListFragmentArgs by navArgs()
        val cardList = args.cardList

        binding.toolbar.title = "최근 추가된 모음"
        binding.toolbar.ivBack.setOnClickListener {
            popBackStack()
        }
        val rv = binding.recyclerView
        val jsonArray = JSONArray(cardList)

        if (jsonArray.length() > 0) {
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

            rv.adapter = RecentlyAddedCampaignAdpater(list)
            rv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        } else {
            binding.llNoAlert.visibility = View.VISIBLE
        }
    }
}