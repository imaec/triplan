package com.imaec.triplan.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaec.domain.model.PlanDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _homeList = MutableLiveData<List<HomeItem>>()
    val homeList: LiveData<List<HomeItem>> get() = _homeList

    fun fetchData() {
        _homeList.value = listOf(
            HomeItem.Plan(
                title = "다가오는 일정",
                list = listOf(
                    PlanDto(
                        planId = 0,
                        planName = "6월 제주 여행",
                        city = "제주",
                        startDate = "2022.06.14",
                        endDate = "2022.06.18"
                    ),
                    PlanDto(
                        planId = 1,
                        planName = "6월 제주 여행",
                        city = "제주",
                        startDate = "2022.06.14",
                        endDate = "2022.06.18"
                    )
                )
            ),
            HomeItem.Divider,
            HomeItem.Plan(
                title = "다녀온 일정",
                list = listOf(
                    PlanDto(
                        planId = 2,
                        planName = "6월 제주 여행",
                        city = "제주",
                        startDate = "2022.06.14",
                        endDate = "2022.06.18"
                    ),
                    PlanDto(
                        planId = 3,
                        planName = "6월 제주 여행",
                        city = "제주",
                        startDate = "2022.06.14",
                        endDate = "2022.06.18"
                    )
                )
            )
        )
    }

    fun onClickWrite() {
    }
}
