package com.yunke.lib_common.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Switch
import android.widget.TextView
import com.yunke.lib_common.BaseApplication
import com.yunke.lib_common.R
import com.google.android.material.tabs.TabLayout


object ThemeManage {

    var color = R.color.app_theme_color
    var color16 = color
    private var color00 = color
    private var color32 = color
    private var color64 = color


    fun setThemeColor(colorStr: String?) {
        kotlin.runCatching {
            if (colorStr?.length == 6) {
                color = Color.parseColor("#${colorStr}")
                color00 = Color.parseColor("#00${colorStr}")
                color16 = Color.parseColor("#16${colorStr}")
                color32 = Color.parseColor("#32${colorStr}")
                color64 = Color.parseColor("#64${colorStr}")
            }
        }
    }

    /**
     * 设置文本颜色
     */
    fun setTextColor(vararg tvTextView: View?) {
        kotlin.runCatching {
            tvTextView?.map {
                if (it is TextView) {
                    it?.setTextColor(color)
                }
            }
        }
    }

    /**
     * 设置文本颜色
     */
    fun setRadioButtonColor(vararg tvTextView: View?) {
        kotlin.runCatching {
            tvTextView?.map {
                if (it is RadioButton) {
                    it?.setTextColor(color)
                }
            }
        }
    }

    /**
     * 设置背景颜色
     */
    fun setBackgroundColor(vararg view: View?) {
        kotlin.runCatching {
            view?.map {
                it?.setBackgroundColor(color)
            }
        }
    }

    fun setShapeStrokeColor(vararg view: View?) {
        kotlin.runCatching {
            view?.map {
                val shapeDrawable = it?.background as GradientDrawable
                shapeDrawable.setStroke(BaseApplication.getContext().resources.getDimensionPixelSize(R.dimen.line_width), color)
                it?.background = shapeDrawable
            }
        }

    }

    fun setShapeSolidColor(vararg view: View?) {
        kotlin.runCatching {
            view?.map {
                val shapeDrawable = it?.background as GradientDrawable
                shapeDrawable.setColor(color)
                it?.background = shapeDrawable
            }
        }
    }

    fun setShapeSolidColor(mTabLayout: TabLayout?) {
        kotlin.runCatching {
            val shapeDrawable = mTabLayout?.tabSelectedIndicator as GradientDrawable
            shapeDrawable.setColor(color)
            shapeDrawable.setSize(
                BaseApplication.getContext().resources.getDimensionPixelSize(R.dimen.dp_20),
            BaseApplication.getContext().resources.getDimensionPixelSize(R.dimen.dp_3))
            mTabLayout?.setSelectedTabIndicator(shapeDrawable)
        }
    }

    fun setShapeSolidColor16(vararg view: View?) {
        kotlin.runCatching {
            view?.map {
                val shapeDrawable = it?.background as GradientDrawable
                shapeDrawable.setColor(color16)
                it?.background = shapeDrawable
            }
        }
    }

    fun setShapeSolidColor64(vararg view: View?) {
        kotlin.runCatching {
            view?.map {
                val shapeDrawable = it?.background as GradientDrawable
                shapeDrawable.setColor(color64)
                it?.background = shapeDrawable
            }
        }
    }

    fun setEditCursorColor(vararg view: EditText?) {
        kotlin.runCatching {
            view?.map {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val shapeDrawable = it?.textCursorDrawable as GradientDrawable
                    shapeDrawable.setColor(color)
                    it?.textCursorDrawable = shapeDrawable
                }
            }
        }
    }


    fun setShapeSolidColor(view: View?, mColor: Int) {
        kotlin.runCatching {
            val shapeDrawable = view?.background as GradientDrawable
            shapeDrawable.setColor(mColor)
            view?.background = shapeDrawable
        }
    }

    fun setShapeColor(view: View?, strokeColor: Int, solidColor: Int) {
        kotlin.runCatching {
            val shapeDrawable = view?.background as GradientDrawable
            shapeDrawable.setStroke(BaseApplication.getContext().resources.getDimensionPixelSize(R.dimen.line_width), strokeColor)
            shapeDrawable.setColor(solidColor)
            view?.background = shapeDrawable
        }
    }

    fun setShapeGradientColor(view: View?) {
        kotlin.runCatching {
            val shapeDrawable = view?.background as GradientDrawable
            val colors = intArrayOf(color32, color00)
            shapeDrawable.colors = colors
            view?.background = shapeDrawable
        }
    }

    fun setSwitchColor(view: Switch?) {
        kotlin.runCatching {
            val shapeDrawable = view?.trackDrawable as GradientDrawable
            shapeDrawable.setColor(color)
            view?.trackDrawable = shapeDrawable
        }
    }
}