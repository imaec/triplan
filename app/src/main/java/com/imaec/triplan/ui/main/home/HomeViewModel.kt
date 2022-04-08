package com.imaec.triplan.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.usecase.plan.GetPlanListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlanListUseCase: GetPlanListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> get() = _state

    private val _homeList = MutableLiveData<List<HomeItem>>()
    val homeList: LiveData<List<HomeItem>> get() = _homeList

    fun fetchData() {
        viewModelScope.launch {
            getPlanListUseCase().collect {
                when (it) {
                    is Result.Success -> {
                        it.data.sortedBy {
                            it.startDate
                        }.groupBy {
                            val today = LocalDate.now()
                            when {
                                it.startDate == today ||
                                    it.endDate == today ||
                                    (it.startDate.isBefore(today) && it.endDate.isAfter(today)) -> {
                                    // 현재 일정
                                    HomePlanType.CURRENT
                                }
                                it.startDate.isAfter(today) -> {
                                    // 다가오는 일정
                                    HomePlanType.UPCOMING
                                }
                                else -> {
                                    // 다녀온 일정, it.endDate.isAfter(today)
                                    HomePlanType.PAST
                                }
                            }
                        }.let { result ->
                            val tempList = mutableListOf<HomeItem>()
                            result.keys.sortedBy {
                                it.ordinal
                            }.forEach {
                                result[it]?.let { list ->
                                    if (list.isNotEmpty()) {
                                        tempList.add(HomeItem.Plan(it, list.take(2)))
                                    }
                                }
                                if (tempList.isNotEmpty()) tempList.add(HomeItem.Divider)
                            }

                            _homeList.value = tempList
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun onClickWrite() {
        _state.value = HomeState.OnClickWrite
    }
}
