package com.imaec.domain.usecase.recentkeyword

import com.imaec.domain.IoDispatcher
import com.imaec.domain.NoParamUseCase
import com.imaec.domain.Result
import com.imaec.domain.repository.RecentKeywordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentKeywordListUseCase @Inject constructor(
    private val repository: RecentKeywordRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<Flow<Result<List<String>>>>(dispatcher) {

    override suspend fun execute() = repository.getRecentKeywordList()
}
