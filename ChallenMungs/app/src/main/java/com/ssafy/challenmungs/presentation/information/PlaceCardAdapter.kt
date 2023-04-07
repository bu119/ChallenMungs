package com.ssafy.challenmungs.presentation.information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemPlaceListCardBinding

class PlaceCardAdapter(private val arrayList: List<Map<String, Any>>) :
    RecyclerView.Adapter<PlaceCardViewHolder>() {

    private lateinit var binding: ItemPlaceListCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceCardViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_place_list_card,
            parent,
            false
        )
        return PlaceCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceCardViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int = arrayList.size
}
class PlaceCardViewHolder(private val binding: ItemPlaceListCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(dto: Map<String, Any>) {
        binding.tvPlaceName.text = dto.get("name").toString()
        binding.tvPlaceType.text = dto.get("type").toString()
        binding.tvPlaceAddress.text = dto.get("address").toString()
        binding.tvPlacePhone.text = dto.get("number").toString()
        initListener()
    }
    private fun initListener() {
        binding.root.setOnClickListener {
            // 상세 페이지로 이동하는 navigation 코드 구현 필요
        }
    }
}