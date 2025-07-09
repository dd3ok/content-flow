package com.dd3ok.contentflow.domain.content.controller.dto

import com.dd3ok.contentflow.domain.content.repository.ContentStatus
import java.time.LocalDateTime

data class ContentResponse(
    val id: String,
    val title: String,
    val body: String,
    val authorId: Long,
    val status: ContentStatus,
    val tags: List<String>,
    val assetIds: List<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val version: Int
)