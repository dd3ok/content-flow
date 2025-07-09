package com.dd3ok.contentflow.domain.automation.controller.dto

import com.dd3ok.contentflow.domain.automation.repository.AutomationType

data class AutomationCreateRequest(
    val sourceContentId: String,
    val type: AutomationType
)
