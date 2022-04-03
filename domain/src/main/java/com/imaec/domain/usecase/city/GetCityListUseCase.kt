package com.imaec.domain.usecase.city

import com.imaec.domain.IoDispatcher
import com.imaec.domain.NoParamUseCase
import com.imaec.domain.Result
import com.imaec.domain.model.CityDto
import com.imaec.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityListUseCase @Inject constructor(
    private val repository: CityRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<Flow<Result<List<CityDto>>>>(dispatcher) {

    override suspend fun execute(): Flow<Result<List<CityDto>>> = repository.getCityList()
}
