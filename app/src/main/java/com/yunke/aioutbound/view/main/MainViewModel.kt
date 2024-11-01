package com.yunke.aioutbound.view.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yunke.module_base.vm.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * @author Linker
 * @description:
 * @date :2024/10/31
 */
class MainViewModel : BaseViewModel() {
    val testlv = MutableLiveData<String>()

    fun getItem(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            testlv.postValue("test 显示")
            testlv.value="trstst"//
        }
    }
}