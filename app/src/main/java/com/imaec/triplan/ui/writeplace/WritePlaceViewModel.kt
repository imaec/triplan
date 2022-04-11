package com.imaec.triplan.ui.writeplace

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.model.CityDto
import com.imaec.domain.model.NaverPlaceDto
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.usecase.category.AddCategoryUseCase
import com.imaec.domain.usecase.city.AddCityUseCase
import com.imaec.domain.usecase.naverplace.GetNaverPlaceUseCase
import com.imaec.domain.usecase.place.EditPlaceUseCase
import com.imaec.domain.usecase.place.SavePlaceUseCase
import com.imaec.triplan.ui.writeplace.WritePlaceActivity.Companion.PLACE
import com.imaec.triplan.ui.writeplace.WritePlaceActivity.Companion.TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritePlaceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val addCityUseCase: AddCityUseCase,
    private val getNaverPlaceUseCase: GetNaverPlaceUseCase,
    private val savePlaceUseCase: SavePlaceUseCase,
    private val editPlaceUseCase: EditPlaceUseCase
) : ViewModel() {

    private val place = savedStateHandle.get<PlaceDto>(PLACE)
    private val type = savedStateHandle.get<WritePlaceType>(TYPE) ?: WritePlaceType.WRITE

    private val _state = MutableLiveData<WritePlaceState>()
    val state: LiveData<WritePlaceState> get() = _state

    private val _category = MutableLiveData(
        place?.let {
            CategoryDto(it.categoryId, it.category)
        } ?: run {
            CategoryDto(-1, "")
        }
    )
    val category: LiveData<CategoryDto> get() = _category

    private val _city = MutableLiveData(
        place?.let {
            CityDto(it.cityId, it.city)
        } ?: run {
            CityDto(-1, "")
        }
    )
    val city: LiveData<CityDto> get() = _city

    private val _address = MutableLiveData(place?.address ?: "")
    val address: LiveData<String> get() = _address

    private val _site = MutableLiveData(place?.siteUrl ?: "")
    val site: LiveData<String> get() = _site

    val placeName = ObservableField(place?.placeName ?: "")

    fun setCategory(category: CategoryDto) {
        _category.value = category
    }

    fun setCity(city: CityDto) {
        _city.value = city
    }

    fun getAddressNotDefault() = address.value?.let {
        if (it == "주소를 입력해주세요.") {
            ""
        } else {
            it
        }
    } ?: run {
        ""
    }

    fun setAddress(address: String) {
        _address.value = address
    }

    fun setPlace(naverPlace: NaverPlaceDto) {
        placeName.set(naverPlace.title)
        val address = naverPlace.address ?: naverPlace.roadAddress ?: ""
        if (address.isNotBlank()) {
            _address.value = address
        }
        _site.value = naverPlace.link
    }

    fun saveCategory(category: String) {
        viewModelScope.launch {
            addCategoryUseCase(category)?.let {
                _category.value = it
            }
        }
    }

    fun saveCity(city: String) {
        viewModelScope.launch {
            addCityUseCase(city)?.let {
                _city.value = it
            }
        }
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
        val keyword = placeName.get() ?: ""
        if (keyword.length <= 1) {
            _state.value = WritePlaceState.OnError("검색 할 장소를 2글자 이상 입력해주세요.")
            return
        }

        viewModelScope.launch {
            getNaverPlaceUseCase(keyword).collect {
                when (it) {
                    is Result.Success -> {
                        _state.value = WritePlaceState.OnLoadNaverPlace(it.data)
                    }
                    else -> {}
                }
            }
        }
    }

    fun onClickAddress() {
        _state.value = WritePlaceState.OnClickAddress
    }

    fun onClickSave() {
        val checkResult = checkInputValue()
        if (checkResult.isNotEmpty()) {
            _state.value = WritePlaceState.OnError(checkResult)
            return
        }
        viewModelScope.launch {
            var place = PlaceDto(
                placeId = place?.placeId ?: -1,
                categoryId = category.value?.categoryId ?: -1,
                category = category.value?.category ?: "",
                cityId = city.value?.cityId ?: -1,
                city = city.value?.city ?: "",
                placeName = placeName.get() ?: "",
                address = address.value ?: "",
                siteUrl = site.value ?: ""
            )
            place = when (type) {
                WritePlaceType.WRITE -> savePlaceUseCase(place)
                WritePlaceType.EDIT -> editPlaceUseCase(place)
            }
            _state.value = WritePlaceState.OnSuccess(place)
        }
    }

    private fun checkInputValue(): String {
        // 카테고리 선택 확인
        if (category.value?.categoryId ?: -1 == -1L) {
            return "카테고리를 선택해주세요."
        }
        // 지역 선택 확인
        if (city.value?.cityId ?: -1 == -1L) {
            return "지역을 선택해주세요."
        }
        // 장소 이름 입력 확인
        if (placeName.get().isNullOrBlank()) {
            return "장소 이름을 입력해주세요."
        }
        return ""
    }
}
