package com.imaec.triplan.ui.searchaddress

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.RoadAddressDto
import com.imaec.domain.usecase.address.GetAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAddressViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase
) : ViewModel() {

    private val _state = MutableLiveData<SearchAddressState>()
    val state: LiveData<SearchAddressState> get() = _state

    private val _searchAddressList = MutableLiveData<List<SearchAddressItem>>()
    val searchAddressList: LiveData<List<SearchAddressItem>> get() = _searchAddressList

    val address = ObservableField("")

    init {
        _searchAddressList.value = listOf(SearchAddressItem.SearchInput)
        address.addOnPropertyChangedCallback(
            object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    val keyword = address.get() ?: ""
                    if (keyword.length > 1) searchAddress(keyword)
                }
            }
        )
    }

    private fun searchAddress(keyword: String) {
        viewModelScope.launch {
            getAddressUseCase(keyword).collect { result ->
                when (result) {
                    is Result.Success -> {
                        val tempList = mutableListOf<SearchAddressItem>()
                        tempList.add(SearchAddressItem.SearchInput)
                        result.data.forEach {
                            tempList.add(SearchAddressItem.Address(it))
                        }
                        _searchAddressList.value = tempList
                    }
                    Result.Loading -> {
                    }
                    Result.Empty -> {
                        _searchAddressList.value = listOf(SearchAddressItem.SearchInput)
                    }
                    is Result.Error -> {
                        _state.value = SearchAddressState.OnError(
                            result.exception.message ?: "오류가 발생했습니다. 다시 시도해주세요."
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun onClickAddress(addressDto: RoadAddressDto) {
        val address = addressDto.jibunAddr ?: addressDto.roadAddr ?: ""
        if (address.isNotEmpty()) _state.value = SearchAddressState.OnClickAddress(address)
    }
}
