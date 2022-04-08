package com.imaec.data.repository

import com.imaec.data.db.dao.CategoryDao
import com.imaec.data.entity.local.CategoryEntity
import com.imaec.data.entity.local.CategoryEntity.Companion.toDto
import com.imaec.domain.Result
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class CategoryRepositoryImpl(
    private val dao: CategoryDao
) : CategoryRepository {

    override suspend fun addCategory(category: String): CategoryDto? {
        if (dao.getCountByCategory(category) == 0) {
            val entity = CategoryEntity(category = category)
            val result = dao.insert(entity)
            return toDto(entity.copy(categoryId = result))
        }
        return null
    }

    override fun getCategoryList() = flow {
        emit(Result.Loading)
        dao.getCategoryList().collect {
            if (it.isEmpty()) {
                emit(Result.Empty)
            } else {
                emit(Result.Success(it.map(::toDto)))
            }
        }
    }.catch { e ->
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateCategory(category: CategoryDto) {
        dao.update(CategoryEntity.fromDto(category))
    }

    override suspend fun deleteCategory(category: CategoryDto) {
        dao.delete(CategoryEntity.fromDto(category))
    }
}
