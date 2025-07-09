package com.dd3ok.contentflow.domain.asset.repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "assets")
data class Asset(
    @Id val id: String? = null,
    val originalFileName: String,
    val storedFileName: String,
    val url: String,
    val mimeType: String?,
    val size: Long,
    val uploaderId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now()
)