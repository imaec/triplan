package com.imaec.triplan.ui.category

import com.imaec.domain.model.CategoryDto

sealed class CategoryManagementState {

    data class OnError(val message: String) : CategoryManagementState()
    object OnClickAdd : CategoryManagementState()
    data class OnClickCategory(val category: CategoryDto) : CategoryManagementState()
}
