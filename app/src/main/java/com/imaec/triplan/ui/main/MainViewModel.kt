package com.imaec.triplan.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.CityDto
import com.imaec.domain.usecase.city.AddAllCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addAllCityUseCase: AddAllCityUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            addAllCityUseCase(
                listOf(
                    CityDto(1, "서울", true),
                    CityDto(2, "제주", true),
                    CityDto(3, "가평·양평", true),
                    CityDto(4, "강릉·속초", true),
                    CityDto(5, "춘천·홍천", true),
                    CityDto(6, "전주", true),
                    CityDto(7, "경주", true),
                    CityDto(8, "인청", true),
                    CityDto(9, "여수", true),
                    CityDto(10, "통영·거제·남해", true),
                    CityDto(11, "포항·안동", true)
                )
            )
        }
    }
}
