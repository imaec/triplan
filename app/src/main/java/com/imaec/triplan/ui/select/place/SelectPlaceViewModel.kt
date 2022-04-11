package com.imaec.triplan.ui.select.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.usecase.place.GetPlaceListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectPlaceViewModel @Inject constructor(
    private val getPlaceListUseCase: GetPlaceListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<SelectPlaceState>()
    val state: LiveData<SelectPlaceState> get() = _state

    private val _placeList = MutableLiveData<List<PlaceDto>>()
    val placeList: LiveData<List<PlaceDto>> get() = _placeList

    private val _visibleEmpty = MutableLiveData(false)
    val visibleEmpty: LiveData<Boolean> get() = _visibleEmpty

    fun fetchData() {
        viewModelScope.launch {
            getPlaceListUseCase().collect {
                when (it) {
                    is Result.Success -> {
                        _placeList.value = it.data ?: emptyList()
                        _visibleEmpty.value = false
                    }
                    Result.Empty -> {
                        _placeList.value = emptyList()
                        _visibleEmpty.value = true
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun onClickPlace(place: PlaceDto) {
        _state.value = SelectPlaceState.OnClickPlace(place)
    }

    fun onClickAddPlace() {
        _state.value = SelectPlaceState.OnClickAddPlace
    }
}
