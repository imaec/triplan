package com.imaec.triplan.ui.main.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.usecase.place.GetPlaceListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getPlaceListUseCase: GetPlaceListUseCase
) : ViewModel() {

    private val _placeResult: MutableStateFlow<Result<List<PlaceDto>>> =
        MutableStateFlow(Result.Init)
    val placeResult = _placeResult.asStateFlow()

    init {
        viewModelScope.launch {
            getPlaceListUseCase().collect {
                _placeResult.value = it
            }
        }
    }
}
