package com.imaec.domain.usecase.address

import com.imaec.domain.IoDispatcher
import com.imaec.domain.Result
import com.imaec.domain.UseCase
import com.imaec.domain.model.RoadAddressDto
import com.imaec.domain.repository.RoadAddressRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val repository: RoadAddressRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, Flow<Result<List<RoadAddressDto>>>>(dispatcher) {

    override suspend fun execute(parameters: String) = repository.getAddress(parameters)
}
