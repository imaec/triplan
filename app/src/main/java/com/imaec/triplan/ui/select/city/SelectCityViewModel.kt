package com.imaec.triplan.ui.select.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.CityDto
import com.imaec.domain.usecase.city.GetCityListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCityViewModel @Inject constructor(
    private val getCityListUseCase: GetCityListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<SelectCityState>()
    val state: LiveData<SelectCityState> get() = _state

    private val _cityList = MutableLiveData<List<CityDto>>()
    val cityList: LiveData<List<CityDto>> get() = _cityList

    fun fetchData() {
        viewModelScope.launch {
            getCityListUseCase().collect { result ->
                when (result) {
                    is Result.Success -> _cityList.value = result.data ?: emptyList()
                    Result.Loading -> {
                    }
                    Result.Empty -> {
                    }
                    is Result.Error -> {
                    }
                    else -> {}
                }
            }
        }
    }

    fun onClickCity(city: CityDto) {
        _state.value = SelectCityState.OnClickCity(city)
    }
}
