package com.imaec.domain.usecase.plan

import com.imaec.domain.IoDispatcher
import com.imaec.domain.Result
import com.imaec.domain.UseCase
import com.imaec.domain.model.PlanDto
import com.imaec.domain.repository.PlanRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlanUseCase @Inject constructor(
    private val repository: PlanRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, Flow<Result<PlanDto>>>(dispatcher) {

    override suspend fun execute(parameters: Long): Flow<Result<PlanDto>> =
        repository.getPlan(parameters)
}
