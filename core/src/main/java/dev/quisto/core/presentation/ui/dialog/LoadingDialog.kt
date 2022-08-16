package dev.quisto.core.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import dev.quisto.core.R


class LoadingDialog(private val context: Context, private val message: String) {
    var loadingDialog: Dialog = Dialog(context)

    fun showDialog() {
        val view: View = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null)
        val messagetext = view.findViewById<TextView>(R.id.textmessage)
        messagetext.text = message
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.5f
        loadingDialog.window!!.attributes = layoutParams
        loadingDialog.setContentView(view)
        loadingDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog.setCancelable(true)
        loadingDialog.show()
    }

    fun dismissDialog() {
        loadingDialog.dismiss()
    }

}

