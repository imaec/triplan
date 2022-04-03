package com.imaec.triplan.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaec.triplan.model.CategoryVo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryManagementViewModel @Inject constructor() : ViewModel() {

    private val _categoryList = MutableLiveData<List<CategoryVo>>()
    val categoryList: LiveData<List<CategoryVo>> get() = _categoryList

    init {
        _categoryList.value = listOf(
            CategoryVo(0, "음식점"),
            CategoryVo(1, "카페"),
            CategoryVo(2, "관광지"),
            CategoryVo(3, "숙소"),
            CategoryVo(4, "놀거리"),
            CategoryVo(5, "방탈출"),
            CategoryVo(6, "영화관"),
            CategoryVo(7, "박물관"),
            CategoryVo(8, "쇼핑"),
            CategoryVo(9, "기타")
        )
    }

    fun onClickDelete(categoryId: Long) {
    }
}
