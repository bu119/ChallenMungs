package com.ssafy.challenmungs.presentation.mypage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.ItemBankStatementDateBinding
import com.ssafy.challenmungs.domain.entity.mypage.BalanceHistory

class BankStatementOuterAdapter() :
    RecyclerView.Adapter<BankStatementOuterAdapter.BankStatementOuterViewHolder>() {

    private lateinit var dataSet: List<BalanceHistory>
    private lateinit var binding: ItemBankStatementDateBinding

    @SuppressLint("NotifyDataSetChanged")
    fun submitListBalanceHistory(list: List<BalanceHistory>) {
        dataSet = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BankStatementOuterViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_bank_statement_date,
            parent,
            false
        )
        return BankStatementOuterViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: BankStatementOuterViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    class BankStatementOuterViewHolder(
        private val binding: ItemBankStatementDateBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: BalanceHistory) {
            binding.history = item
        }
    }
}