package com.yunke.aioutbound.view.aiimport androidx.recyclerview.widget.RecyclerViewimport com.alibaba.android.arouter.facade.annotation.Routeimport com.yunke.aioutbound.databinding.ActivityAiRecordBindingimport com.yunke.lib_common.recycler.SpaceItemDecorationimport com.yunke.lib_common.router.ARouterPathimport com.yunke.lib_common.util.DisplayUtilimport com.yunke.module_base.view.activity.BaseListActivityimport com.yunke.module_base.view.adapter.BaseAdapter/** * @author Linker * @description: AI录音 * @date :2024/11/1 */@Route(path = ARouterPath.APP.AiRecordActivity)class AiRecordActivity : BaseListActivity<ActivityAiRecordBinding, AiRecordViewModel, String>() {    //使用默认标题    override fun isDefaultTitle(): Boolean = true    //赋值recyclerview    override fun getRecyclerView(): RecyclerView? = viewBinding()?.recyclerView    //赋值adapter    override fun getBaseAdapter(): BaseAdapter<String> = AiRecordAdapter()    override fun initData() {        super.initData()//记得调用，初始化recyclerView设置等        getToolBarView()?.setTitleText("AI话术录音")        val top = DisplayUtil.dip2px(getContext(), 5f)        getRecyclerView()?.addItemDecoration(SpaceItemDecoration(top, 0, top, 0))        mAdapter?.addData("开场白话术1")        mAdapter?.addData("开场白话术2")        mAdapter?.addData("开场白话术3")        mAdapter?.addData("开场白话术4")        mAdapter?.addData("开场白话术5")    }    override fun setListener() {    }    /**     * 右上角菜单按钮，根据id区分     */    override fun jumpToPage(viewId: Int) {    }}