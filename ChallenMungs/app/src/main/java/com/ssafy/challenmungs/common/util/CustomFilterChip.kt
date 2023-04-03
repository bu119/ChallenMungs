package com.ssafy.challenmungs.common.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.CustomFilterChipBinding

class CustomFilterChip @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class State {
        NONE, ASC, DESC
    }

    private var binding: CustomFilterChipBinding
    var state: State = State.NONE

    init {
        binding = CustomFilterChipBinding.inflate(LayoutInflater.from(context), this, true)
        setView(attrs)
    }

    private fun setView(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFilterChip)

        binding.ivFilter.setImageDrawable(typedArray.getDrawable(R.styleable.CustomFilterChip_iconRes))
        binding.tvFilter.text = typedArray.getString(R.styleable.CustomFilterChip_text)

        typedArray.recycle()
    }

    fun changeState(state: State) {
        this.state = state
        binding.ivFilterOption.apply {
            visibility = when (state) {
                State.NONE -> {
                    binding.root.setBackgroundResource(R.drawable.bg_rect_white_smoke_white_radius50_stroke1)
                    View.GONE
                }
                State.ASC -> {
                    binding.root.setBackgroundResource(R.drawable.bg_rect_golden_glow_radius50)
                    setImageResource(R.drawable.ic_filter_asc)
                    View.VISIBLE
                }
                State.DESC -> {
                    binding.root.setBackgroundResource(R.drawable.bg_rect_golden_glow_radius50)
                    setImageResource(R.drawable.ic_filter_desc)
                    View.VISIBLE
                }
            }
        }
    }
}