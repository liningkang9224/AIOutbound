package com.yunke.lib_common.animation

import android.view.animation.LinearInterpolator
import kotlin.math.pow
import kotlin.math.sin

/**
 * @auth : linker
 * @date : 2020/8/11 3:01 PM
 * @description ：
 */
class JellyInterpolator(var factor:Float=1.5f): LinearInterpolator() {


    override fun getInterpolation(input: Float): Float {
        return (2.0.pow(-10 * input.toDouble())
                * sin((input - factor / 4) * (2 * Math.PI) / factor) + 1).toFloat()
    }
}