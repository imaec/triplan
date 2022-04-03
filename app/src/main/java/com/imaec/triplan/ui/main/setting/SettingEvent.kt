package com.imaec.triplan.ui.main.setting

sealed class SettingEvent {

    object Init : SettingEvent()
    object OnClickPlaceManagement : SettingEvent()
    object OnClickCityManagement : SettingEvent()
    object OnClickShare : SettingEvent()
}
