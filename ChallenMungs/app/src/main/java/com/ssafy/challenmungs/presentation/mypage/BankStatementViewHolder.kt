package com.ssafy.challenmungs.presentation.mypage

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.databinding.ItemBankStatementBottomBinding
import com.ssafy.challenmungs.databinding.ItemBankStatementMiddleBinding
import com.ssafy.challenmungs.databinding.ItemBankStatementTopBinding
import com.ssafy.challenmungs.domain.entity.bank.BankStatement

sealed class BankStatementViewHolder(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(item: BankStatement)

    class TopItemViewHolder(private val binding: ItemBankStatementTopBinding) :
        BankStatementViewHolder(binding) {

        override fun onBind(item: BankStatement) {}
    }

    class MiddleItemViewHolder(private val binding: ItemBankStatementMiddleBinding) :
        BankStatementViewHolder(binding) {

        override fun onBind(item: BankStatement) {}
    }

    class BottomItemViewHolder(private val binding: ItemBankStatementBottomBinding) :
        BankStatementViewHolder(binding) {

        override fun onBind(item: BankStatement) {}
    }
}
