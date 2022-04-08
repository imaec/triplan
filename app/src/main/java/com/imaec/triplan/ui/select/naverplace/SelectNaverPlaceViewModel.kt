package com.imaec.triplan.ui.select.naverplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.imaec.domain.model.NaverPlaceDto
import com.imaec.triplan.ui.select.naverplace.SelectNaverPlaceBottomSheet.Companion.LIST
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectNaverPlaceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableLiveData<SelectNaverPlaceState>()
    val state: LiveData<SelectNaverPlaceState> get() = _state

    private val _naverPlaceList = MutableLiveData<List<NaverPlaceDto>>(savedStateHandle.get(LIST))
    val naverPlaceList: LiveData<List<NaverPlaceDto>> get() = _naverPlaceList

    fun onClickPlace(place: NaverPlaceDto) {
        _state.value = SelectNaverPlaceState.OnClickPlace(place)
    }
}
