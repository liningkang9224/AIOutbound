package com.yunke.module_base.view.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.yunke.lib_common.listener.OnItemClickListener
import com.yunke.lib_common.listener.ViewClick


/**
 * @author Linker
 * @description: base适配
 *               修改为viewBinding
 * @date :2024/9/16
 */
open abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    private var mList: ArrayList<T> = ArrayList()

    private var mItemListener: OnItemClickListener<T>? = null

    private var mItemTouchListener: OnItemClickListener<T>? = null


    /**
     * viewholder绑定
     */
    override fun onBindViewHolder(holder: BaseViewHolder<T>, @SuppressLint("RecyclerView") position: Int) {
        mItemListener?.let {//item点击事件
            ViewClick.setOnClickListener(holder.itemView) {
                it.onItemClick(position, mList[position])
            }
        }
        mItemTouchListener?.let {//item点击事件，带有动画效果
            ViewClick.setOnTouchListener(holder.itemView) {
                it.onItemClick(position, mList[position])
            }
        }
        holder.bindData(mList[position], position)
    }

    /**
     * 数据绑定
     */
    abstract class BaseViewHolder<T>(viewBinding: ViewBinding) : ViewHolder(viewBinding.root) {
        abstract fun bindData(data: T, position: Int)
    }


    override fun getItemCount(): Int = mList.size

    /**
     * 设置item点击事件
     */
    fun setOnItemClickListener(mItemListener: OnItemClickListener<T>?) {
        this.mItemListener = mItemListener
    }

    /**
     * 设置item点击事件，带有动画
     */
    fun setOnItemAnimListener(mItemListener: OnItemClickListener<T>?) {
        this.mItemTouchListener = mItemListener
    }

    /**
     * 设置数据
     */
    open fun setList(mList: List<T>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    /**
     * 设置指定数据
     */
    open fun setData(position: Int, mData: T?) {
        mData?.let {
            this.mList[position] = it
            notifyItemChanged(position)
        }

    }

    /**
     * 添加集合数据
     */
    open fun addData(mList: List<T>) {
        val index = this.mList.size
        this.mList.addAll(mList)
        if (index > 0) {
            notifyItemRangeChanged(index, this.mList.size)
        } else {
            notifyDataSetChanged()
        }
    }

    /**
     * 是否刷新
     */
    open fun setList(isRefresh: Boolean, mList: List<T>) {
        if (isRefresh) {
            setList(mList)
        } else {
            addData(mList)
        }
    }

    /**
     * 添加单个数据
     */
    open fun addData(mData: T) {
        addData(mList.size, mData)
    }

    open fun clearAllData() {
        this.mList.clear()
        notifyDataSetChanged()
    }

    open fun addData(position: Int, mData: T) {
        this.mList.add(position, mData)
        notifyItemChanged(position)
    }

    open fun getList() = mList


}