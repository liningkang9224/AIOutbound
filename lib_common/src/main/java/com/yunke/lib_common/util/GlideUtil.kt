package com.yunke.lib_common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.yunke.lib_common.BaseApplication
import com.yunke.lib_common.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition


/**
 * @author Linker
 * @description:
 * @date :2022/8/17
 */
object GlideUtil {

    /**
     * 默认配置
     */
    private var options: RequestOptions = RequestOptions().centerCrop()
        .error(R.mipmap.ic_cover_default)
        .placeholder(R.mipmap.ic_cover_default)
        .diskCacheStrategy(DiskCacheStrategy.ALL)


    /**
     * 圆形图片配置
     */
    private var hOptions: RequestOptions = RequestOptions().centerCrop()
        .error(R.mipmap.ic_user_default)
        .placeholder(R.mipmap.ic_user_default)
        .transform(CircleCrop())
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    /**
     * 加载图片，使用默认配置
     */
    fun loadImage(context: Context, url: String?, imageView: ImageView?) {
        loadImage(context, url, imageView, options)
    }

    /**
     * 加载图片，使用自定义预加载图片
     */
    fun loadImage(context: Context, url: String?, resId: Int, imageView: ImageView?) {
        loadImage(context, url, imageView, options.clone().error(resId).placeholder(resId))
    }

    /**
     * 加载圆角图片，使用自定义圆角角度
     */
    fun loadImageRound(context: Context, url: String?, imageView: ImageView?, round: Int) {
        loadImageRound(context, url, imageView, R.mipmap.ic_cover_default, round)
    }

    /**
     * 加载圆角图片，使用自定义圆角角度
     */
    fun loadImageRound(context: Context, url: String?, imageView: ImageView?) {
        loadImageRound(context, url, imageView, BaseApplication.getContext().resources.getDimensionPixelSize(R.dimen.dp_2))
    }

    /**
     * 加载圆角图片，使用自定义圆角角度，传入预加载图片
     * round必须大于0
     */
    fun loadImageRound(context: Context, url: String?, imageView: ImageView?, resId: Int, round: Int) {
        val r = if (round > 0) round else 2
//        val op = options.clone().error(resId).placeholder(resId)
//        op.transform(RoundedCorners(DisplayUtil.dip2px(context, (r).toFloat())))
        val multiTransformation: MultiTransformation<Bitmap> =
            MultiTransformation(CenterCrop(), RoundedCorners(DisplayUtil.dip2px(context, (r).toFloat())))
        loadImage(
            context, url, imageView, RequestOptions.bitmapTransform(multiTransformation).placeholder(resId).error(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
    }

    /**
     * 调用Glide加载图片
     */
    private fun loadImage(context: Context, url: String?, imageView: ImageView?, op: RequestOptions) {
        imageView?.apply {
            Glide.with(context).load(url ?: "")
                .apply(op)
                .into(this)
        }
    }

    /**
     * 加载圆形图片
     */
    fun loadCircleImage(context: Context, url: String?, imageView: ImageView?) {
        loadImage(context, url, imageView, hOptions)
    }


    /**
     * 加载图片，支持回调，返回bitmap
     */
    fun loadImage(context: Context, url: String?, imageView: ImageView?, listener: LoadBitmapListener) {
        Glide.with(context).asBitmap()
            .load(url)
            .apply(options)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageView?.setImageBitmap(resource)
                    listener.loadBitmap(resource)
                }
            })
    }

    /**
     * 加载图片，支持回调，返回bitmap
     */
//    fun loadImage(context: Context, url: String?, imageView: ImageView?, listener: LoadBitmapListener) {
//        imageView?.let {
//            Glide.with(context).asBitmap()
//                .listener(object : RequestListener<Bitmap?> {
//
//                    override fun onLoadFailed(
//                        e: GlideException?,
//                        model: Any?,
//                        target: com.bumptech.glide.request.target.Target<Bitmap?>?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        return false
//                    }
//
//                    override fun onResourceReady(
//                        resource: Bitmap?,
//                        model: Any?,
//                        target: com.bumptech.glide.request.target.Target<Bitmap?>?,
//                        dataSource: DataSource?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        listener.loadBitmap(resource)
//                        return false
//                    }
//                })
//                .load(url)
//                .apply(options)
//                .into(imageView)
//        }
//    }


    /**
     * 加载圆形图片，支持回调，返回bitmap
     */
//    fun loadCircleImage(context: Context, url: String?, imageView: ImageView?, listener: LoadBitmapListener) {
//        imageView?.let {
//            Glide.with(context).asBitmap()
//                .load(url ?: "").apply(hOptions)
//                .listener(object : RequestListener<Bitmap?> {
//                    override fun onLoadFailed(
//                        e: GlideException?,
//                        model: Any?,
//                        target: com.bumptech.glide.request.target.Target<Bitmap?>?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        val resource = (context.getDrawable(R.mipmap.ic_user_default) as BitmapDrawable).bitmap
//                        listener.loadBitmap(resource)
//                        return false
//                    }
//
//                    override fun onResourceReady(
//                        resource: Bitmap?,
//                        model: Any?,
//                        target: com.bumptech.glide.request.target.Target<Bitmap?>?,
//                        dataSource: DataSource?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        listener.loadBitmap(resource)
//                        return false
//                    }
//
//
//                }).into(imageView)
//        }
//    }

    interface LoadBitmapListener {
        fun loadBitmap(bitmap: Bitmap?)
    }


    fun setImageStatus(imageView: ImageView?, isVivid: Boolean) {
        val colorMatrix = ColorMatrix()
        if (isVivid) {
            colorMatrix.setSaturation(1.0f)
        } else {
            colorMatrix.setSaturation(0.0f)
        }
        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        imageView?.colorFilter = colorFilter
    }
}