package com.dd3ok.contentflow.domain.automation.service

import com.dd3ok.contentflow.domain.automation.repository.AutomationRepository
import com.dd3ok.contentflow.domain.automation.repository.AutomationStatus
import com.dd3ok.contentflow.domain.automation.service.event.AutomationCreatedEvent
import com.dd3ok.contentflow.domain.notification.controller.dto.NotificationResponse
import com.dd3ok.contentflow.domain.notification.service.NotificationService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AutomationEventListener(
    private val automationRepository: AutomationRepository,
    private val notificationService: NotificationService
) {
    @Async
    @EventListener
    fun handleAutomationCreatedEvent(event: AutomationCreatedEvent) {
        try {
            // 데모를 위한 예외 발생 로직
            if (event.sourceContentBody.contains("error")) {
                throw IllegalStateException("AI 모델 호출 중 에러 발생!")
            }

            Thread.sleep(3000) // 3초간 AI 작업 시뮬레이션
            val summary = "${event.sourceContentBody.take(15)}... 요약 완료!"
            updateStatus(event, AutomationStatus.SUCCESS, result = summary)

        } catch (e: Exception) {
            updateStatus(event, AutomationStatus.FAILED, errorMessage = e.message)
        }
    }

    private fun updateStatus(
        event: AutomationCreatedEvent,
        status: AutomationStatus,
        result: String? = null,
        errorMessage: String? = null
    ) {
        automationRepository.findById(event.automationId)
            .flatMap { automation ->
                val updated = automation.copy(
                    status = status,
                    result = result,
                    errorMessage = errorMessage,
                    finishedAt = LocalDateTime.now()
                )
                automationRepository.save(updated)
            }
            .doOnSuccess {
                // DB 업데이트 성공 후, 알림 발송!
                sendNotification(event, status, errorMessage)
            }
            .subscribe()
    }

    private fun sendNotification(event: AutomationCreatedEvent, status: AutomationStatus, errorMessage: String?) {
        val notificationResponse = if (status == AutomationStatus.SUCCESS) {
            NotificationResponse("AUTOMATION_SUCCESS", "콘텐츠 요약이 완료되었습니다.", event.sourceContentId)
        } else {
            NotificationResponse("AUTOMATION_FAILED", "콘텐츠 요약에 실패했습니다: ${errorMessage}", event.sourceContentId)
        }
        notificationService.createAndNotify(event.authorId, notificationResponse)
    }
}