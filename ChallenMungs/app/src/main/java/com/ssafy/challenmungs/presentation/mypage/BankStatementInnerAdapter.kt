package com.ssafy.challenmungs.presentation.mypage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemBankStatementItemBinding
import com.ssafy.challenmungs.domain.entity.mypage.BalanceDetail

class BankStatementInnerAdapter() :
    RecyclerView.Adapter<BankStatementInnerAdapter.BankStatementInnerViewHolder>() {

    private lateinit var detailDataSet: List<BalanceDetail>
    private lateinit var binding: ItemBankStatementItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun submitDetailList(list: List<BalanceDetail>) {
        detailDataSet = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BankStatementInnerViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_bank_statement_item,
            parent,
            false
        )
        return BankStatementInnerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BankStatementInnerViewHolder,
        position: Int
    ) {
        holder.onBind(detailDataSet[position])
    }

    override fun getItemCount(): Int = detailDataSet.size

    class BankStatementInnerViewHolder(private val binding: ItemBankStatementItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: BalanceDetail) {
            binding.historyDetail = item
        }
    }
}