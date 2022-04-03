package com.imaec.domain.model

data class SettingDto(
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
