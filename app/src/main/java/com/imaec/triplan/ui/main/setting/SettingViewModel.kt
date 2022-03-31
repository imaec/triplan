package com.imaec.triplan.ui.main.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaec.triplan.model.SettingButtonAction
import com.imaec.triplan.model.SettingVo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() : ViewModel() {

    private val _settingList = MutableLiveData<List<SettingVo>>()
    val settingList: LiveData<List<SettingVo>> get() = _settingList

    fun fetchData(version: String) {
        _settingList.value = listOf(
            SettingVo(title = "카테고리 관리", type = SettingButtonAction.PLACE_MANAGEMENT),
            SettingVo(title = "지역 관리", type = SettingButtonAction.CITY_MANAGEMENT),
            SettingVo(title = "앱 버전", version = version, type = SettingButtonAction.NONE),
            SettingVo(title = "앱 공유하기", type = SettingButtonAction.SHARE)
        )
    }

    fun onClickSettingButton(type: SettingButtonAction) {
    }
}
