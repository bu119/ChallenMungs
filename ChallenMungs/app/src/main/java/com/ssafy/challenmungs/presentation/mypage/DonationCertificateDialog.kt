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
import com.ssafy.challenmungs.databinding.DialogDonationCertificateBinding

class DonationCertificateDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogDonationCertificateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_donation_certificate,
            null, false
        )
        setContentView(binding.root)
        initSetting()
    }

    private fun initSetting() {
        context.dialogResize(this, 0.8f)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }
}