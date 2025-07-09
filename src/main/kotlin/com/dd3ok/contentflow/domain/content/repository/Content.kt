package com.dd3ok.contentflow.domain.content.repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "contents")
data class Content(
    @Id
    val id: String? = null,
    val title: String,
    val body: String,
    val authorId: Long,
    val status: ContentStatus = ContentStatus.DRAFT,
    val tags: List<String> = emptyList(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val version: Int = 1
)