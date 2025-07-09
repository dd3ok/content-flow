package com.dd3ok.contentflow.domain.exhibition.repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "exhibitions")
data class Exhibition(
    @Id
    val id: String? = null,
    val contentId: String,
    val displayOrder: Int,
    val isVisible: Boolean = true,
    val channel: ExhibitionChannel,
    val exposureStart: LocalDateTime? = null,
    val exposureEnd: LocalDateTime? = null
)
