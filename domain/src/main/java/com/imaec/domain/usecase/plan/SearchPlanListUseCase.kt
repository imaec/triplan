package com.imaec.domain.usecase.plan

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.model.PlanDto
import com.imaec.domain.model.SearchParam
import com.imaec.domain.repository.PlanRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchPlanListUseCase @Inject constructor(
    private val repository: PlanRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<SearchParam, List<PlanDto>>(dispatcher) {

    override suspend fun execute(parameters: SearchParam): List<PlanDto> =
        repository.searchPlanList(parameters.keyword, parameters.moreResult)
}
