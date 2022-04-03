package com.imaec.triplan.ui.category

import com.imaec.domain.model.CategoryDto

sealed class CategoryManagementItem {
    object CategoryInput : CategoryManagementItem()
    data class Category(val category: CategoryDto) : CategoryManagementItem()
}
