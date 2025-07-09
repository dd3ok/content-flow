package com.dd3ok.contentflow.domain.notification.repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "notifications")
data class Notification(
    @Id
    val id: String? = null,
    val userId: Long,
    val type: String,
    val message: String,
    val contentId: String,
    val isRead: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)