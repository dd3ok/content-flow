package com.dd3ok.contentflow.domain.automation.service.event

data class AutomationCreatedEvent(
    val automationId: String,
    val sourceContentId: String,
    val sourceContentBody: String,
    val authorId: Long
)