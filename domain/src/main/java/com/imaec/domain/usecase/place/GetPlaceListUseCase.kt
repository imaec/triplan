package com.imaec.domain.usecase.place

import androidx.lifecycle.LiveData
import com.imaec.domain.IoDispatcher
import com.imaec.domain.NoParamUseCase
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.repository.PlaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPlaceListUseCase @Inject constructor(
    private val repository: PlaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<LiveData<List<PlaceDto>>>(dispatcher) {

    override suspend fun execute(): LiveData<List<PlaceDto>> = repository.getPlaceList()
}
