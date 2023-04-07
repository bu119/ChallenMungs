package com.ssafy.challenmungs.presentation.information

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentHomeBinding
import com.ssafy.challenmungs.databinding.FragmentInformationPlaceListBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.home.MyChallengeListAdapter
import org.json.JSONArray

class InformationPlaceListFragment : BaseFragment<FragmentInformationPlaceListBinding>(R.layout.fragment_information_place_list) {

    override fun initView() {
        val args: InformationPlaceListFragmentArgs by navArgs()
        val list = args.cardList
        binding.ivEscapeButton.setOnClickListener {
            popBackStack()
        }
        var jsonArray = JSONArray(list)
        val dtoList = mutableListOf<Map<String, Any>>()

        for (i in 0 until jsonArray.length()) {
            val jsonObj = jsonArray.getJSONObject(i)
            val map = mutableMapOf<String, Any>()
            val keys = jsonObj.keys()

            while (keys.hasNext()) {
                val key = keys.next()
                val value = jsonObj.get(key)
                map.put(key, value)
            }
            dtoList.add(map)
        }

        val rv = binding.rvPlaceListCard
        val adapter = PlaceCardAdapter(dtoList)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}