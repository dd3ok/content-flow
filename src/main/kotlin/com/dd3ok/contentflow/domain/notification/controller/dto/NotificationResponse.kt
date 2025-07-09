package com.dd3ok.contentflow.domain.notification.controller.dto

data class NotificationResponse(
    val type: String, // e.g., "AUTOMATION_SUCCESS", "AUTOMATION_FAILED"
    val message: String,
    val contentId: String
)