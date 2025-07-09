package com.dd3ok.contentflow.domain.meta.service.mapper

import com.dd3ok.contentflow.domain.meta.controller.dto.MetaResponse
import com.dd3ok.contentflow.domain.meta.repository.Meta

fun Meta.toResponse(): MetaResponse =
    MetaResponse(
        id = this.id ?: "",
        name = this.name,
        type = this.type
    )
