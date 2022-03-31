package com.imaec.data.repository

import com.imaec.data.db.dao.PlaceDao
import com.imaec.data.entity.PlaceEntity.Companion.toDto
import com.imaec.domain.Result
import com.imaec.domain.repository.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class PlaceRepositoryImpl(
    private val dao: PlaceDao
) : PlaceRepository {

    override fun getPlaceList() = flow {
        emit(Result.Loading)
        dao.getPlaceList().collect {
            if (it.isEmpty()) {
                emit(Result.Empty)
            } else {
                emit(Result.Success(it.map(::toDto)))
            }
        }
    }.catch { e ->
        Result.Error(Exception(e))
    }.flowOn(Dispatchers.IO)
}
