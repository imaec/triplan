package com.imaec.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.imaec.data.db.dao.PlaceDao
import com.imaec.data.entity.PlaceEntity.Companion.toDto
import com.imaec.domain.model.PlaceDto
import com.imaec.domain.repository.PlaceRepository

class PlaceRepositoryImpl(
    private val dao: PlaceDao
) : PlaceRepository {

    override fun getPlaceList(): LiveData<List<PlaceDto>> = Transformations.map(dao.getPlaceList()) {
        it.map(::toDto)
    }
}
