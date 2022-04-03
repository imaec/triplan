package com.imaec.domain.repository

import com.imaec.domain.Result
import com.imaec.domain.model.CategoryDto
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun addCategory(category: String)

    fun getCategoryList(): Flow<Result<List<CategoryDto>>>

    suspend fun updateCategory(category: CategoryDto)

    suspend fun deleteCategory(category: CategoryDto)
}
