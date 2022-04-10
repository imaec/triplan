package com.imaec.triplan.ui.category

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.Result
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.usecase.category.AddCategoryUseCase
import com.imaec.domain.usecase.category.DeleteCategoryUseCase
import com.imaec.domain.usecase.category.GetCategoryListUseCase
import com.imaec.domain.usecase.category.UpdateCategoryUseCase
import com.imaec.domain.usecase.place.GetPlaceCountByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryManagementViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val getPlaceCountByCategoryUseCase: GetPlaceCountByCategoryUseCase
) : ViewModel() {

    private val _state = MutableLiveData<CategoryManagementState>()
    val state: LiveData<CategoryManagementState> = _state

    private val _categoryList = MutableLiveData<List<CategoryManagementItem>>()
    val categoryList: LiveData<List<CategoryManagementItem>> get() = _categoryList

    val category = ObservableField("")

    init {
        viewModelScope.launch {
            getCategoryListUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        val tempList = mutableListOf<CategoryManagementItem>()
                        tempList.add(CategoryManagementItem.CategoryInput)
                        result.data.forEach {
                            tempList.add(CategoryManagementItem.Category(it))
                        }
                        _categoryList.value = tempList
                    }
                    Result.Loading -> {
                    }
                    Result.Empty -> {
                        _categoryList.value = listOf(CategoryManagementItem.CategoryInput)
                    }
                    is Result.Error -> {
                        _state.value = CategoryManagementState.OnError(
                            result.exception.message ?: "오류가 발생했습니다. 다시 시도해주세요."
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun updateCategory(categoryId: Long, category: String) {
        viewModelScope.launch {
            updateCategoryUseCase(CategoryDto(categoryId, category))
        }
    }

    fun onClickAdd() {
        if (category.get().isNullOrBlank()) {
            _state.value = CategoryManagementState.OnError("카테고리를 입력 해주세요.")
        } else {
            viewModelScope.launch {
                addCategoryUseCase(category.get()!!)
                category.set("")
                _state.value = CategoryManagementState.OnClickAdd
            }
        }
    }

    fun onClickDelete(category: CategoryDto) {
        viewModelScope.launch {
            if (getPlaceCountByCategoryUseCase(category.category) > 0) {
                _state.value = CategoryManagementState.OnError("카테고리를 사용하고있는 장소가 있습니다.")
                return@launch
            }
            deleteCategoryUseCase(category)
        }
    }

    fun onClickCategory(category: CategoryDto) {
        _state.value = CategoryManagementState.OnClickCategory(category)
    }
}
