package com.imaec.domain.usecase.place

import com.imaec.domain.IoDispatcher
import com.imaec.domain.NoParamUseCase
import com.imaec.domain.Result
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaceListUseCase @Inject constructor(
    private val repository: PlaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<Flow<Result<List<PlaceDto>>>>(dispatcher) {

    override suspend fun execute(): Flow<Result<List<PlaceDto>>> = repository.getPlaceList()
}
