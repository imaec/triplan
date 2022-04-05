package com.imaec.triplan.ui.main.myplace

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
class MyPlaceViewModel @Inject constructor(
    private val getPlaceListUseCase: GetPlaceListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<MyPlaceState>()
    val state: LiveData<MyPlaceState> get() = _state

    private val _placeList = MutableLiveData<List<PlaceDto>>()
    val placeList: LiveData<List<PlaceDto>> get() = _placeList

    fun fetchData() {
        viewModelScope.launch {
            getPlaceListUseCase().collect {
                when (it) {
                    is Result.Success -> {
                        _placeList.value = it.data ?: emptyList()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun onClickWrite() {
        _state.value = MyPlaceState.OnClickWrite
    }
}
