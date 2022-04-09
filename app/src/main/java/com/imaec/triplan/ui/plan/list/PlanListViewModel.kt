package com.imaec.triplan.ui.plan.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.imaec.domain.model.PlanDayDto
import com.imaec.domain.model.PlanItemDto
import com.imaec.triplan.ext.DATE_PATTERN_MM_DD_E
import com.imaec.triplan.ext.dateToStringFormat
import com.imaec.triplan.ui.plan.list.PlanListFragment.Companion.PLAN_DAY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _planDay = MutableLiveData<PlanDayDto>(savedStateHandle.get(PLAN_DAY))
    val planDay: LiveData<PlanDayDto> get() = _planDay

    private val _planItemList = MutableLiveData<List<PlanListItem>>()
    val planItemList: LiveData<List<PlanListItem>> get() = _planItemList

    init {
        // TODO 임시 리스트
        _planItemList.value = listOf(
            PlanListItem.PlanItem(
                1,
                PlanItemDto(
                    itemNo = 1,
                    placeName = "PlaceName 1",
                    city = "제주",
                    category = "기타",
                    address = "제주",
                    time = "1시",
                    description = "",
                    cost = ""
                )
            ),
            PlanListItem.PlanItem(
                2,
                PlanItemDto(
                    itemNo = 2,
                    placeName = "PlaceName 1",
                    city = "제주",
                    category = "기타",
                    address = "제주",
                    time = "1시",
                    description = "",
                    cost = ""
                )
            ),
            PlanListItem.PlanItem(
                3,
                PlanItemDto(
                    itemNo = 3,
                    placeName = "PlaceName 2",
                    city = "제주",
                    category = "음식점",
                    address = "제주",
                    time = "1시",
                    description = "",
                    cost = ""
                )
            ),
            PlanListItem.PlanItem(
                4,
                PlanItemDto(
                    itemNo = 4,
                    placeName = "PlaceName 3",
                    city = "서귀포",
                    category = "숙소",
                    address = "제주",
                    time = "1시",
                    description = "",
                    cost = ""
                )
            ),
            PlanListItem.PlanItem(
                5,
                PlanItemDto(
                    itemNo = 5,
                    placeName = "PlaceName 4",
                    city = "제주",
                    category = "기타",
                    address = "제주",
                    time = "1시",
                    description = "",
                    cost = "",
                    lastItem = true
                )
            ),
            PlanListItem.AddButton
        )
    }

    fun title(): String = "DAY ${planDay.value?.planDay} - " +
        planDay.value?.planDate?.dateToStringFormat(DATE_PATTERN_MM_DD_E)

    fun onClickAdd() {
    }
}
