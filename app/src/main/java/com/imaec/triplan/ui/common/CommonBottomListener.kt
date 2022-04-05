package com.imaec.triplan.ui.common

import java.io.Serializable

interface CommonBottomListener<DTO : Any> : Serializable {
    fun onBottomSelected(data: DTO)
}
