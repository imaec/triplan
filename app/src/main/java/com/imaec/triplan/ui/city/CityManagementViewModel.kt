package com.imaec.triplan.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaec.domain.model.CityDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CityManagementViewModel @Inject constructor() : ViewModel() {

    private val _cityList = MutableLiveData<List<CityDto>>()
    val cityList: LiveData<List<CityDto>> get() = _cityList

    init {
        _cityList.value = listOf(
            CityDto(0, "서울", true),
            CityDto(1, "제주", true),
            CityDto(2, "가평·양평", true),
            CityDto(3, "강릉·속초", true),
            CityDto(4, "춘천·홍천", true),
            CityDto(5, "전주", true),
            CityDto(6, "경주", true),
            CityDto(7, "인청", true),
            CityDto(8, "여수", true),
            CityDto(9, "통영·거제·남해", true),
            CityDto(10, "포항·안동", true)
        )
    }

    fun onClickDelete(cityId: Long) {
    }
}
