package com.imaec.triplan.ui.plan.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.model.PlanDto
import com.imaec.domain.model.PlanItemDto
import com.imaec.domain.usecase.plan.UpdatePlanUseCase
import com.imaec.triplan.ui.plan.write.PlanWriteActivity.Companion.PLAN
import com.imaec.triplan.ui.plan.write.PlanWriteActivity.Companion.PLAN_DAY
import com.imaec.triplan.ui.plan.write.PlanWriteActivity.Companion.PLAN_ITEM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanWriteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updatePlanUseCase: UpdatePlanUseCase
) : ViewModel() {

    private val _state = MutableLiveData<PlanWriteState>()
    val state: LiveData<PlanWriteState> get() = _state

    private val _planItem = MutableLiveData(
        savedStateHandle.get(PLAN_ITEM) ?: PlanItemDto(
            itemNo = 0,
            placeName = "",
            city = "",
            category = "",
            address = "",
            time = "",
            description = "",
            cost = "",
            lastItem = false
        )
    )
    val planItem: LiveData<PlanItemDto> get() = _planItem

    val plan = savedStateHandle.get<PlanDto>(PLAN)

    val planDay = savedStateHandle.get<Int>(PLAN_DAY) ?: 1

    fun setCategory(category: String) {
        _planItem.value = planItem.value?.copy(category = category)
    }

    fun setCity(city: String) {
        _planItem.value = planItem.value?.copy(city = city)
    }

    fun getAddressNotDefault() = planItem.value?.address?.let {
        if (it == "주소를 입력해주세요.") {
            ""
        } else {
            it
        }
    } ?: run {
        ""
    }

    fun setAddress(address: String) {
        _planItem.value = planItem.value?.copy(address = address)
    }

    fun setPlace(place: PlaceDto) {
        _planItem.value = planItem.value?.copy(
            placeName = place.placeName,
            city = place.city,
            category = place.category,
            address = place.address
        )
    }

    fun onClickCategory() {
        _state.value = PlanWriteState.OnClickCategory
    }

    fun onClickAddCategory() {
        _state.value = PlanWriteState.OnClickAddCategory
    }

    fun onClickCity() {
        _state.value = PlanWriteState.OnClickCity
    }

    fun onClickAddCity() {
        _state.value = PlanWriteState.OnClickAddCity
    }

    fun onClickPlace() {
        _state.value = PlanWriteState.OnClickPlace
    }

    fun onClickAddPlace() {
        _state.value = PlanWriteState.OnClickAddPlace
    }

    fun onClickAddress() {
        _state.value = PlanWriteState.OnClickAddress
    }

    fun onClickSave() {
        viewModelScope.launch {
            val planDayList = plan?.planDayList?.mapIndexed { index, planDayDto ->
                if (index == planDay - 1) {
                    val itemNo = (planDayDto.planItemList.lastOrNull()?.itemNo ?: 0) + 1
                    val planItemList = mutableListOf<PlanItemDto>()
                    if (planItem.value?.itemNo == 0) {
                        // 일정 추가
                        planDayDto.planItemList.forEach {
                            planItemList.add(it)
                        }
                        planItemList.add(planItem.value?.copy(itemNo = itemNo)!!)
                    } else {
                        // 일정 수정
                        planItemList.addAll(
                            planDayDto.planItemList.map {
                                if (it.itemNo == planItem.value?.itemNo) {
                                    planItem.value ?: it // Update
                                } else {
                                    it
                                }
                            }
                        )
                    }
                    planDayDto.copy(planItemList = planItemList)
                } else {
                    planDayDto
                }
            }

            val plan = plan?.copy(
                planDayList = planDayList ?: emptyList()
            ) ?: return@launch
            updatePlanUseCase(plan)
            _state.value = PlanWriteState.OnClickSave
        }
    }
}
