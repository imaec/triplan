package com.imaec.triplan.ui.main.myplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaec.domain.model.PlaceDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPlaceViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<MyPlaceState>()
    val state: LiveData<MyPlaceState> get() = _state

    private val _placeList = MutableLiveData<List<PlaceDto>>()
    val placeList: LiveData<List<PlaceDto>> get() = _placeList

    fun onClickWrite() {
        _state.value = MyPlaceState.OnClickWrite
    }
}
