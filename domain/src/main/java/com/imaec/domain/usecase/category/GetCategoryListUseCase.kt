package com.imaec.domain.usecase.category

import com.imaec.domain.IoDispatcher
import com.imaec.domain.NoParamUseCase
import com.imaec.domain.Result
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(
    private val repository: CategoryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<Flow<Result<List<CategoryDto>>>>(dispatcher) {

    override suspend fun execute(): Flow<Result<List<CategoryDto>>> = repository.getCategoryList()
}
