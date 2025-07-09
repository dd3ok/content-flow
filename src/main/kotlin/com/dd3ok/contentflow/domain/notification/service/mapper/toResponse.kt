package com.dd3ok.contentflow.domain.notification.service.mapper

import com.dd3ok.contentflow.domain.notification.controller.dto.NotificationResponse
import com.dd3ok.contentflow.domain.notification.repository.Notification

fun Notification.toResponse(): NotificationResponse = NotificationResponse(
    type = this.type,
    message = this.message,
    contentId = this.contentId
)