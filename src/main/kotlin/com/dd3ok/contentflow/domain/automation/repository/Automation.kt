package com.dd3ok.contentflow.domain.automation.repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "automations")
data class Automation(
    @Id
    val id: String? = null,
    val sourceContentId: String,      // 원본 콘텐츠 id
    val type: AutomationType,
    val status: AutomationStatus = AutomationStatus.PENDING,
    val result: String? = null,       // 생성된 요약문, 썸네일 주소, 번역 결과 등
    val requestedAt: LocalDateTime = LocalDateTime.now(),
    val finishedAt: LocalDateTime? = null,
    val errorMessage: String? = null  // 실패시 에러 메시지
)
