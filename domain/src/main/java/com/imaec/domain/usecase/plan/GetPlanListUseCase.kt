package com.imaec.domain.usecase.plan

import com.imaec.domain.IoDispatcher
import com.imaec.domain.NoParamUseCase
import com.imaec.domain.Result
import com.imaec.domain.model.PlanDto
import com.imaec.domain.repository.PlanRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlanListUseCase @Inject constructor(
    private val repository: PlanRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<Flow<Result<List<PlanDto>>>>(dispatcher) {

    override suspend fun execute(): Flow<Result<List<PlanDto>>> = repository.getPlanList()
}
