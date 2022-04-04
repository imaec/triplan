package com.imaec.triplan.ui.writeplace.searchaddress

import com.imaec.domain.model.RoadAddressDto

sealed class SearchAddressItem {
    object SearchInput : SearchAddressItem()
    data class Address(val address: RoadAddressDto) : SearchAddressItem()
}
