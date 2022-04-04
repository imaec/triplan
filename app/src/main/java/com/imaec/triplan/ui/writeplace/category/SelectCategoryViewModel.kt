package com.imaec.triplan.ui.writeplace.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.usecase.category.GetCategoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCategoryViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<SelectCategoryState>()
    val state: LiveData<SelectCategoryState> get() = _state

    private val _categoryList = MutableLiveData<List<CategoryDto>>()
    val categoryList: LiveData<List<CategoryDto>> get() = _categoryList

    fun fetchData() {
        viewModelScope.launch {
            getCategoryListUseCase().collect { result ->
                when (result) {
                    is Result.Success -> _categoryList.value = result.data ?: emptyList()
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

    fun onClickCategory(category: CategoryDto) {
        _state.value = SelectCategoryState.OnClickCategory(category)
    }
}
