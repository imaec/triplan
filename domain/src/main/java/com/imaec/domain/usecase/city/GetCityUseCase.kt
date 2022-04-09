package com.imaec.domain.usecase.city

import com.imaec.domain.IoDispatcher
import com.imaec.domain.Result
import com.imaec.domain.UseCase
import com.imaec.domain.model.CityDto
import com.imaec.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityUseCase @Inject constructor(
    private val repository: CityRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, Flow<Result<CityDto>>>(dispatcher) {

    override suspend fun execute(parameters: String): Flow<Result<CityDto>> =
        repository.getCity(parameters)
}
