package com.yunke.aioutbound.view.main.homeimport BaseFragmentimport com.yunke.aioutbound.databinding.FragmentHomeBindingimport com.yunke.aioutbound.util.ARouterJumpimport com.yunke.lib_common.listener.ViewClickimport com.yunke.lib_common.router.ARouterPathimport com.yunke.module_base.view.dialog.EditDialogimport com.yunke.module_base.view.dialog.MessageDialogimport com.yunke.module_base.view.fragment.ViewModelFragment/** * @author Linker * @description: 首页 * @date :2024/11/1 */class HomeFragment : ViewModelFragment<FragmentHomeBinding, HomeViewModel>() {    override fun initData() {    }    override fun loadData() {    }    override fun setListener() {        ViewClick.setOnTouchListener(viewBinding()?.tvTest, { aiRecord() })    }    private fun aiRecord() {//        viewModel()?.getImei(mContext)//        viewModel()?.setPhoneNumberMask(mContext)//        ARouterJump.jumpPage(mContext, ARouterPath.APP.AiRecordActivity, null)        EditDialog(mContext).onShow().setTitle("确定删除开场白话术吗？")    }}