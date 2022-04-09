package com.imaec.triplan.ui.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.model.SearchParam
import com.imaec.domain.usecase.place.DeletePlaceUseCase
import com.imaec.domain.usecase.place.SearchPlaceListUseCase
import com.imaec.triplan.ui.place.PlaceMoreActivity.Companion.KEYWORD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceMoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchPlaceListUseCase: SearchPlaceListUseCase,
    private val deletePlaceUseCase: DeletePlaceUseCase
) : ViewModel() {

    private val _state = MutableLiveData<PlaceMoreState>()
    val state: LiveData<PlaceMoreState> get() = _state

    private val _placeList = MutableLiveData<List<PlaceDto>>()
    val placeList: LiveData<List<PlaceDto>> get() = _placeList

    private val keyword = savedStateHandle.get<String>(KEYWORD)

    fun fetchData() {
        viewModelScope.launch {
            val list = searchPlaceListUseCase(SearchParam("%$keyword%", true))
            _placeList.value = list
        }
    }

    fun deletePlace(place: PlaceDto) {
        viewModelScope.launch {
            deletePlaceUseCase(place)
            _state.value = PlaceMoreState.DeletedPlace("\"${place.placeName}\"이(가) 삭제되었습니다.")
        }
    }

    fun onClickPlace(place: PlaceDto) {
        _state.value = PlaceMoreState.OnClickPlace(place)
    }

    fun onLongClickPlace(place: PlaceDto): Boolean {
        _state.value = PlaceMoreState.OnLongClickPlace(place)
        return false
    }
}
