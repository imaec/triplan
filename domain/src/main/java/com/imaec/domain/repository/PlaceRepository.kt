package com.imaec.domain.repository

import androidx.lifecycle.LiveData
import com.imaec.domain.model.PlaceDto

interface PlaceRepository {

    fun getPlaceList(): LiveData<List<PlaceDto>>
}
