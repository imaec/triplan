package com.imaec.domain.usecase.plan

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.model.PlanDto
import com.imaec.domain.repository.PlanRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdatePlanUseCase @Inject constructor(
    private val repository: PlanRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<PlanDto, Unit>(dispatcher) {

    override suspend fun execute(parameters: PlanDto) = repository.updatePlan(parameters)
}
