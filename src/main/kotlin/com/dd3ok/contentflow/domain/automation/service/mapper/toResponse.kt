package com.dd3ok.contentflow.domain.automation.service.mapper

import com.dd3ok.contentflow.domain.automation.controller.dto.AutomationResponse
import com.dd3ok.contentflow.domain.automation.repository.Automation

fun Automation.toResponse(): AutomationResponse =
    AutomationResponse(
        id = this.id ?: "",
        sourceContentId = this.sourceContentId,
        type = this.type,
        status = this.status,
        result = this.result,
        requestedAt = this.requestedAt,
        finishedAt = this.finishedAt,
        errorMessage = this.errorMessage
    )
