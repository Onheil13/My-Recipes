package dev.quisto.core.util

import android.os.Build
import android.text.Html
import android.widget.TextView

object HtmlToText {

    fun TextView.parseHtml(string: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.text = Html.fromHtml(string, Html.FROM_HTML_MODE_COMPACT)
        } else {
            this.text = Html.fromHtml(string)
        }
    }


}