package com.imaec.domain.usecase.naverplace

import com.imaec.domain.IoDispatcher
import com.imaec.domain.Result
import com.imaec.domain.UseCase
import com.imaec.domain.model.NaverPlaceDto
import com.imaec.domain.repository.NaverLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNaverPlaceUseCase @Inject constructor(
    private val repository: NaverLocalRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, Flow<Result<List<NaverPlaceDto>>>>(dispatcher) {

    override suspend fun execute(parameters: String) = repository.getNaverPlace(parameters)
}
