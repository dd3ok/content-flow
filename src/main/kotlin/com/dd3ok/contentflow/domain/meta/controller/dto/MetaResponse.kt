package com.dd3ok.contentflow.domain.meta.controller.dto

import com.dd3ok.contentflow.domain.meta.repository.MetaType

data class MetaResponse(
    val id: String,
    val name: String,
    val type: MetaType
)
