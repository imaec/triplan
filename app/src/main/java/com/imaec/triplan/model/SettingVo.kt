package com.imaec.triplan.model

data class SettingVo(
    val title: String,
    val version: String = "",
    val type: SettingButtonAction
)

enum class SettingButtonAction {
    NONE,
    PLACE_MANAGEMENT,
    CITY_MANAGEMENT,
    SHARE
}
