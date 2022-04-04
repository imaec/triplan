package com.imaec.domain.usecase.city

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.model.CityDto
import com.imaec.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AddCityUseCase @Inject constructor(
    private val repository: CityRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, CityDto?>(dispatcher) {

    override suspend fun execute(parameters: String) = repository.addCity(parameters)
}
