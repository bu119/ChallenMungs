package com.ssafy.challenmungs.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.domain.entity.BankStatementItem

class BankStatementAdapter(
    private val dataSet: ArrayList<BankStatementItem>
) : RecyclerView.Adapter<BankStatementViewHolder>() {

    enum class ViewType {
        TOP,
        MIDDLE,
        BOTTOM,
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankStatementViewHolder =
        when (viewType) {
            ViewType.TOP.ordinal -> {
                BankStatementViewHolder.TopItemViewHolder(
                    getViewDataBinding(parent, R.layout.item_bank_statement_top)
                )
            }
            ViewType.BOTTOM.ordinal -> {
                BankStatementViewHolder.BottomItemViewHolder(
                    getViewDataBinding(parent, R.layout.item_bank_statement_bottom)
                )
            }
            else -> {
                BankStatementViewHolder.MiddleItemViewHolder(
                    getViewDataBinding(parent, R.layout.item_bank_statement_middle)
                )
            }
        }

    override fun onBindViewHolder(holder: BankStatementViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> ViewType.TOP.ordinal
        itemCount -> ViewType.BOTTOM.ordinal
        else -> ViewType.MIDDLE.ordinal
    }

    private fun <T : ViewDataBinding> getViewDataBinding(parent: ViewGroup, layoutRes: Int): T =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes,
            parent,
            false
        )
}