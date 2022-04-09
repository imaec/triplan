package com.imaec.domain.usecase.recentkeyword

import com.imaec.domain.IoDispatcher
import com.imaec.domain.UseCase
import com.imaec.domain.repository.RecentKeywordRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteKeywordUseCase @Inject constructor(
    private val repository: RecentKeywordRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, Unit>(dispatcher) {

    override suspend fun execute(parameters: String) = repository.deleteKeyword(parameters)
}
