package com.imaec.triplan.ui.main.myplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.usecase.place.DeletePlaceUseCase
import com.imaec.domain.usecase.place.GetPlaceListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPlaceViewModel @Inject constructor(
    private val getPlaceListUseCase: GetPlaceListUseCase,
    private val deletePlaceUseCase: DeletePlaceUseCase
) : ViewModel() {

    private val _state = MutableLiveData<MyPlaceState>()
    val state: LiveData<MyPlaceState> get() = _state

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

    fun deletePlace(place: PlaceDto) {
        viewModelScope.launch {
            deletePlaceUseCase(place)
            _state.value = MyPlaceState.DeletedPlace("\"${place.placeName}\"이(가) 삭제되었습니다.")
        }
    }

    fun onClickWrite() {
        _state.value = MyPlaceState.OnClickWrite
    }

    fun onClickPlace(place: PlaceDto) {
        _state.value = MyPlaceState.OnClickPlace(place)
    }

    fun onLongClickPlace(place: PlaceDto): Boolean {
        _state.value = MyPlaceState.OnLongClickPlace(place)
        return false
    }
}
