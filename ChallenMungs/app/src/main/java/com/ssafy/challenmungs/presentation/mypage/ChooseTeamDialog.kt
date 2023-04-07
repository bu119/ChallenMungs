package com.ssafy.challenmungs.presentation.mypage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.common.util.DialogSizeHelper.dialogResize
import com.ssafy.challenmungs.databinding.DialogChooseTeamBinding

class ChooseTeamDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogChooseTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_choose_team,
            null, false
        )
        setContentView(binding.root)
        initSetting()
    }

    private fun initSetting() {
        context.dialogResize(this, 0.8f)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }
}