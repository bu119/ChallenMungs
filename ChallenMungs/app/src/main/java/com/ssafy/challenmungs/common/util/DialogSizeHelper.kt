package com.ssafy.challenmungs.common.util

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

object DialogSizeHelper {

    fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width: Float, height: Float) {
        val window = dialogFragment.dialog?.window
        val x = (getDeviceWidthPx(dialogFragment.requireContext()) * width).toInt()
        val y = (getDeviceHeightPx(dialogFragment.requireContext()) * height).toInt()

        window?.setLayout(x, y)
    }

    fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width: Float) {
        val window = dialogFragment.dialog?.window
        val x = (getDeviceWidthPx(dialogFragment.requireContext()) * width).toInt()

        window?.setLayout(x, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    fun Context.dialogResize(dialog: Dialog, width: Float, height: Float) {
        val window = dialog.window
        val x = (getDeviceWidthPx(dialog.context) * width).toInt()
        val y = (getDeviceHeightPx(dialog.context) * height).toInt()

        window?.setLayout(x, y)
    }

    fun Context.dialogResize(dialog: Dialog, width: Float) {
        val window = dialog.window
        val x = (getDeviceWidthPx(dialog.context) * width).toInt()

        window?.setLayout(x, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}