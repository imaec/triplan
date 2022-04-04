package com.imaec.triplan.ui.writeplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.model.CityDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WritePlaceViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<WritePlaceState>()
    val state: LiveData<WritePlaceState> get() = _state

    private val _category = MutableLiveData(CategoryDto(-1, "카테고리를 선택해주세요."))
    val category: LiveData<CategoryDto> get() = _category

    private val _city = MutableLiveData(CityDto(-1, "지역을 선택해주세요."))
    val city: LiveData<CityDto> get() = _city

    private val _address = MutableLiveData("주소를 입력해주세요.")
    val address: LiveData<String> get() = _address

    fun setCategory(category: CategoryDto) {
        _category.value = category
    }

    fun setCity(city: CityDto) {
        _city.value = city
    }

    fun setAddress(address: String) {
        _address.value = address
    }

    fun onClickCategory() {
        _state.value = WritePlaceState.OnClickCategory
    }

    fun onClickAddCategory() {
        _state.value = WritePlaceState.OnClickAddCategory
    }

    fun onClickCity() {
        _state.value = WritePlaceState.OnClickCity
    }

    fun onClickAddCity() {
        _state.value = WritePlaceState.OnClickAddCity
    }

    fun onClickSearchPlace() {
    }

    fun onClickAddress() {
        _state.value = WritePlaceState.OnClickAddress
    }
}
