package com.dd3ok.contentflow.domain.exhibition.controller.request

import com.dd3ok.contentflow.domain.exhibition.repository.ExhibitionChannel
import java.time.LocalDateTime

data class ExhibitionCreateRequest(
    val contentId: String,
    val displayOrder: Int,
    val channel: ExhibitionChannel,
    val exposureStart: LocalDateTime? = null,
    val exposureEnd: LocalDateTime? = null
)
