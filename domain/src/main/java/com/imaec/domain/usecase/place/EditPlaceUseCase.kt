package com.imaec.domain.usecase.place

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class EditPlaceUseCase @Inject constructor(
    private val repository: PlaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<PlaceDto, PlaceDto>(dispatcher) {

    override suspend fun execute(parameters: PlaceDto): PlaceDto = repository.editPlace(parameters)
}
