package com.dd3ok.contentflow.domain.exhibition.service.mapper

import com.dd3ok.contentflow.domain.exhibition.controller.response.ExhibitionResponse
import com.dd3ok.contentflow.domain.exhibition.repository.Exhibition

fun Exhibition.toResponse(): ExhibitionResponse =
    ExhibitionResponse(
        id = this.id ?: "",
        contentId = this.contentId,
        displayOrder = this.displayOrder,
        isVisible = this.isVisible,
        channel = this.channel,
        exposureStart = this.exposureStart,
        exposureEnd = this.exposureEnd
    )
