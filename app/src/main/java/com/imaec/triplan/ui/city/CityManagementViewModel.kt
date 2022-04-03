package com.imaec.triplan.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaec.triplan.model.CityVo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CityManagementViewModel @Inject constructor() : ViewModel() {

    private val _cityList = MutableLiveData<List<CityVo>>()
    val cityList: LiveData<List<CityVo>> get() = _cityList

    init {
        _cityList.value = listOf(
            CityVo(0, "서울", true),
            CityVo(1, "제주", true),
            CityVo(2, "가평·양평", true),
            CityVo(3, "강릉·속초", true),
            CityVo(4, "춘천·홍천", true),
            CityVo(5, "전주", true),
            CityVo(6, "경주", true),
            CityVo(7, "인청", true),
            CityVo(8, "여수", true),
            CityVo(9, "통영·거제·남해", true),
            CityVo(10, "포항·안동", true)
        )
    }

    fun onClickDelete(cityId: Long) {
    }
}
