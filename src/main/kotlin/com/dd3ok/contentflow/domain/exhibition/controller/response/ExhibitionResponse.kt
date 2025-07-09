package com.dd3ok.contentflow.domain.exhibition.controller.response

import com.dd3ok.contentflow.domain.exhibition.repository.ExhibitionChannel
import java.time.LocalDateTime

data class ExhibitionResponse(
    val id: String,
    val contentId: String,
    val displayOrder: Int,
    val isVisible: Boolean,
    val channel: ExhibitionChannel,
    val exposureStart: LocalDateTime? = null,
    val exposureEnd: LocalDateTime? = null
)
