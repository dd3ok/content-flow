package com.dd3ok.contentflow.domain.automation.controller.dto

import com.dd3ok.contentflow.domain.automation.repository.AutomationStatus
import java.time.LocalDateTime

data class AutomationUpdateRequest(
    val status: AutomationStatus,
    val result: String?,
    val finishedAt: LocalDateTime? = null,
    val errorMessage: String? = null
)
