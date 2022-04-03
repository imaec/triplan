package com.imaec.triplan.ui.main.myplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPlaceViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<MyPlaceState>()
    val state: LiveData<MyPlaceState> get() = _state

    fun onClickWrite() {
        _state.value = MyPlaceState.OnClickWrite
    }
}
