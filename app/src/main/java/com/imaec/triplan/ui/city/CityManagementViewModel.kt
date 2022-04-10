package com.imaec.triplan.ui.city

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.CityDto
import com.imaec.domain.usecase.city.AddCityUseCase
import com.imaec.domain.usecase.city.DeleteCityUseCase
import com.imaec.domain.usecase.city.GetCityListUseCase
import com.imaec.domain.usecase.place.GetPlaceCountByCityUseCase
import com.imaec.domain.usecase.city.UpdateCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityManagementViewModel @Inject constructor(
    private val addCityUseCase: AddCityUseCase,
    private val getCityListUseCase: GetCityListUseCase,
    private val updateCityUseCase: UpdateCityUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
    private val getPlaceCountByCityUseCase: GetPlaceCountByCityUseCase
) : ViewModel() {

    private val _state = MutableLiveData<CityManagementState>()
    val state: LiveData<CityManagementState> = _state

    private val _cityList = MutableLiveData<List<CityManagementItem>>()
    val cityList: LiveData<List<CityManagementItem>> get() = _cityList

    val city = ObservableField("")

    fun fetchData() {
        viewModelScope.launch {
            getCityListUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        val tempList = mutableListOf<CityManagementItem>()
                        tempList.add(CityManagementItem.CityInput)
                        result.data.forEach {
                            tempList.add(CityManagementItem.City(it))
                        }
                        _cityList.value = tempList
                    }
                    Result.Loading -> {
                    }
                    Result.Empty -> {
                        _cityList.value = listOf(CityManagementItem.CityInput)
                    }
                    is Result.Error -> {
                        _state.value = CityManagementState.OnError(
                            result.exception.message ?: "오류가 발생했습니다. 다시 시도해주세요."
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun updateCity(cityId: Long, city: String) {
        viewModelScope.launch {
            updateCityUseCase(CityDto(cityId, city))
        }
    }

    fun onClickAdd() {
        if (city.get().isNullOrBlank()) {
            _state.value = CityManagementState.OnError("지역을 입력 해주세요.")
        } else {
            viewModelScope.launch {
                addCityUseCase(city.get()!!)
                city.set("")
                _state.value = CityManagementState.OnClickAdd
            }
        }
    }

    fun onClickDelete(city: CityDto) {
        viewModelScope.launch {
            if (getPlaceCountByCityUseCase(city.city) > 0) {
                _state.value = CityManagementState.OnError("지역을 사용하고있는 장소가 있습니다.")
                return@launch
            }
            deleteCityUseCase(city)
        }
    }

    fun onClickCity(city: CityDto) {
        if (!city.defaultCity) _state.value = CityManagementState.OnClickCity(city)
    }
}
