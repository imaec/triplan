package com.imaec.domain.model

data class SearchParam(
    val keyword: String,
    val moreResult: Boolean = false
)
