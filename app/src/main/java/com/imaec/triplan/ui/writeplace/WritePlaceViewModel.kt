package com.imaec.triplan.ui.writeplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WritePlaceViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<WritePlaceState>()
    val state: LiveData<WritePlaceState> get() = _state

    private val _address = MutableLiveData("주소를 입력해주세요.")
    val address: LiveData<String> get() = _address

    fun setAddress(address: String) {
        _address.value = address
    }

    fun onClickAddress() {
        _state.value = WritePlaceState.OnClickAddress
    }
}
