package com.dd3ok.contentflow.domain.automation.controller.dto

import com.dd3ok.contentflow.domain.automation.repository.AutomationStatus
import com.dd3ok.contentflow.domain.automation.repository.AutomationType
import java.time.LocalDateTime

data class AutomationResponse(
    val id: String,
    val sourceContentId: String,
    val type: AutomationType,
    val status: AutomationStatus,
    val result: String?,
    val requestedAt: LocalDateTime,
    val finishedAt: LocalDateTime?,
    val errorMessage: String?
)
