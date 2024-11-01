package com.yunke.lib_common.util

import android.graphics.drawable.Drawable
import android.text.Html
import android.text.Spanned
import java.io.InputStream
import java.net.URL

object HtmlUtil {

    /**
     * 需要在子线程调用
     */
    fun from(html: String?): Spanned {
        var sp: Spanned = Html.fromHtml(
            html, Html.FROM_HTML_MODE_COMPACT,
            { source ->
                var `is`: InputStream? = null
                try {
                    `is` = URL(source).content as InputStream
                    val d = Drawable.createFromStream(`is`, "src")
                    d?.setBounds(
                        0, 0, d.intrinsicWidth,
                        d.intrinsicHeight
                    )
                    `is`.close()
                    d
                } catch (e: Exception) {
                    null
                }
            },
            null,
        )
        return sp
    }
}