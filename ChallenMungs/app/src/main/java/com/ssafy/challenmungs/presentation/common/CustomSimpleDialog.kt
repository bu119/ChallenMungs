package com.ssafy.challenmungs.presentation.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.DialogSizeHelper.dialogResize
import com.ssafy.challenmungs.databinding.DialogSimpleCustomBinding

class CustomSimpleDialog(
    context: Context,
    private val customSimpleDialogInterface: CustomSimpleDialogInterface,
    private val single: Boolean,
    private val content: String,
    private val positiveMessage: String
) : Dialog(context) {

    private lateinit var binding: DialogSimpleCustomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_simple_custom,
            null, false
        )
        setContentView(binding.root)
        initSetting()
        initText()
        initListener()
    }

    private fun initSetting() {
        context.dialogResize(this, 0.8f)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    private fun initListener() {
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }

            btnOk.setOnClickListener {
                dismiss()
                customSimpleDialogInterface.onPositiveButton()
            }
        }
    }

    private fun initText() {
        binding.apply {
            tvContent.text = content
            btnOk.text = positiveMessage
            if (single)
                binding.btnCancel.visibility = View.GONE
        }
    }
}