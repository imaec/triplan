package com.imaec.triplan.ui.select.category

import com.imaec.domain.model.CategoryDto

sealed class SelectCategoryState {

    data class OnClickCategory(val category: CategoryDto) : SelectCategoryState()
}
