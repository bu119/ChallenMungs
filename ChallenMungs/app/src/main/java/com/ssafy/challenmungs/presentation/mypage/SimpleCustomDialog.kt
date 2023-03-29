package com.ssafy.challenmungs.presentation.mypage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.ssafy.challenmungs.databinding.DialogSimpleCustomBinding

class SimpleCustomDialog(
    context: Context,
    private val customDialogInterface: CustomDialogInterface,
    private val content: String,
    private val positiveMessage: String
) : Dialog(context) {

    private lateinit var binding: DialogSimpleCustomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initText()
        initListener()
    }

    private fun initListener() {
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnOk.setOnClickListener {
                dismiss()
                customDialogInterface.onPositiveButton()
            }
        }
    }

    private fun initText() {
        binding.apply {
            tvContent.text = content
            btnOk.text = positiveMessage
        }
    }
}