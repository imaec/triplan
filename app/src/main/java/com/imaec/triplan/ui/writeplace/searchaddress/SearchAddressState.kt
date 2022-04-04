package com.imaec.triplan.ui.writeplace.searchaddress

sealed class SearchAddressState {

    data class OnClickAddress(val address: String) : SearchAddressState()
    data class OnError(val message: String) : SearchAddressState()
}
