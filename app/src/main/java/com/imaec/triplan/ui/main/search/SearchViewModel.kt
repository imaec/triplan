package com.imaec.triplan.ui.main.search

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.model.PlanDto
import com.imaec.domain.model.SearchParam
import com.imaec.domain.usecase.place.SearchPlaceListUseCase
import com.imaec.domain.usecase.plan.SearchPlanListUseCase
import com.imaec.domain.usecase.recentkeyword.DeleteKeywordUseCase
import com.imaec.domain.usecase.recentkeyword.GetRecentKeywordListUseCase
import com.imaec.domain.usecase.recentkeyword.SaveKeywordUseCase
import com.imaec.triplan.ext.DATE_PATTERN_yyyy_MM_dd_E
import com.imaec.triplan.ext.dateToStringFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getRecentKeywordListUseCase: GetRecentKeywordListUseCase,
    private val saveKeywordUseCase: SaveKeywordUseCase,
    private val deleteKeywordUseCase: DeleteKeywordUseCase,
    private val searchPlanListUseCase: SearchPlanListUseCase,
    private val searchPlaceListUseCase: SearchPlaceListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState> get() = _state

    private val _recentList = MutableLiveData<List<String>>(emptyList())
    val recentList: LiveData<List<String>> get() = _recentList

    private val _visibleEmptyRecent = MutableLiveData(false)
    val visibleEmptyRecent: LiveData<Boolean> get() = _visibleEmptyRecent

    private val _searchResultList = MutableLiveData<List<SearchItem>>()
    val searchResultList: LiveData<List<SearchItem>> get() = _searchResultList

    val search = ObservableField("")

    fun getDateString(startDate: Long, endDate: Long): String =
        if (startDate == endDate) {
            startDate.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)
        } else {
            "${startDate.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)} ~ " +
                endDate.dateToStringFormat(DATE_PATTERN_yyyy_MM_dd_E)
        }

    fun existSearchResult() = if (searchResultList.value.isNullOrEmpty()) {
        true
    } else {
        _searchResultList.value = emptyList()
        search.set("")
        false
    }

    fun fetchData() {
        viewModelScope.launch {
            getRecentKeywordListUseCase().collect {
                when (it) {
                    is Result.Success -> {
                        _recentList.value = it.data ?: emptyList()
                        _visibleEmptyRecent.value = false
                    }
                    Result.Empty -> {
                        _recentList.value = emptyList()
                        _visibleEmptyRecent.value = true
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun search(keyword: String?) {
        if (keyword.isNullOrBlank()) return

        viewModelScope.launch {
            saveKeywordUseCase(keyword)

            val planDeferred = async {
                searchPlanListUseCase(SearchParam("%$keyword%"))
            }
            val placeDeferred = async {
                searchPlaceListUseCase(SearchParam("%$keyword%"))
            }

            val listPlan = planDeferred.await()
            val listPlace = placeDeferred.await()

            val tempList = mutableListOf<SearchItem>()
            if (listPlan.isNotEmpty()) tempList.add(SearchItem.SearchResultPlan(listPlan))
            if (listPlace.isNotEmpty()) tempList.add(SearchItem.SearchResultPlace(listPlace))
            _searchResultList.value = tempList
        }
        _state.value = SearchState.OnClickSearch
    }

    fun onClickSearch() {
        search(search.get())
    }

    fun onClickKeyword(keyword: String) {
        search(keyword)
    }

    fun onClickDeleteKeyword(keyword: String) {
        viewModelScope.launch {
            deleteKeywordUseCase(keyword)
        }
    }

    fun onClickPlan(plan: PlanDto) {
        _state.value = SearchState.OnClickPlan(plan)
    }

    fun onClickPlanMore() {
        _state.value = SearchState.OnClickPlanMore(search.get())
    }

    fun onClickPlace(place: PlaceDto) {
        _state.value = SearchState.OnClickPlace(place)
    }

    fun onClickPlaceMore() {
        _state.value = SearchState.OnClickPlaceMore(search.get())
    }
}
