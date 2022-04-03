package com.imaec.domain.usecase.city

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.model.CityDto
import com.imaec.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(
    private val repository: CityRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<CityDto, Unit>(dispatcher) {

    override suspend fun execute(parameters: CityDto) = repository.deleteCity(parameters)
}
