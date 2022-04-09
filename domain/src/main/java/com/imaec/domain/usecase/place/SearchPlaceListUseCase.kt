package com.imaec.domain.usecase.place

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchPlaceListUseCase @Inject constructor(
    private val repository: PlaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, List<PlaceDto>>(dispatcher) {

    override suspend fun execute(parameters: String): List<PlaceDto> =
        repository.searchPlaceList(parameters)
}
