package com.imaec.domain.usecase.category

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.model.CategoryDto
import com.imaec.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<CategoryDto, Unit>(dispatcher) {

    override suspend fun execute(parameters: CategoryDto) = repository.updateCategory(parameters)
}
