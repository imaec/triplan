package com.imaec.triplan.ui.main.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.SettingButtonAction
import com.imaec.domain.model.SettingDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<SettingEvent>()
    val event: SharedFlow<SettingEvent> = _event.asSharedFlow()

    private val _settingList = MutableLiveData<List<SettingDto>>()
    val settingList: LiveData<List<SettingDto>> get() = _settingList

    fun fetchData(version: String) {
        _settingList.value = listOf(
            SettingDto(title = "카테고리 관리", type = SettingButtonAction.PLACE_MANAGEMENT),
            SettingDto(title = "지역 관리", type = SettingButtonAction.CITY_MANAGEMENT),
            SettingDto(title = "앱 버전", version = version, type = SettingButtonAction.NONE),
            SettingDto(title = "앱 공유하기", type = SettingButtonAction.SHARE)
        )
    }

    fun onClickSettingButton(type: SettingButtonAction) {
        viewModelScope.launch {
            _event.emit(
                when (type) {
                    SettingButtonAction.PLACE_MANAGEMENT -> SettingEvent.OnClickPlaceManagement
                    SettingButtonAction.CITY_MANAGEMENT -> SettingEvent.OnClickCityManagement
                    SettingButtonAction.SHARE -> SettingEvent.OnClickShare
                    else -> return@launch
                }
            )
        }
    }
}
