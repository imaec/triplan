package com.imaec.domain.model

import java.io.Serializable

data class CategoryDto(
    val categoryId: Long,
    val category: String
) : Serializable
