package com.dd3ok.contentflow.domain.notification.service

import com.dd3ok.contentflow.common.sse.NotificationEvent
import com.dd3ok.contentflow.common.sse.SseHandler
import com.dd3ok.contentflow.domain.notification.controller.dto.NotificationResponse
import com.dd3ok.contentflow.domain.notification.repository.Notification
import com.dd3ok.contentflow.domain.notification.repository.NotificationRepository
import com.dd3ok.contentflow.domain.notification.service.mapper.toResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class NotificationService(
    private val sseHandler: SseHandler,
    private val notificationRepository: NotificationRepository
) {
    fun createAndNotify(userId: Long, response: NotificationResponse) {
        val notification = Notification(
            userId = userId,
            type = response.type,
            message = response.message,
            contentId = response.contentId
        )

        notificationRepository.save(notification)
            .doOnSuccess {
                // 저장 성공 후, 새로운 이벤트 객체를 만들어 전송
                val event = NotificationEvent("notification", response)
                sseHandler.send(userId.toString(), event)
            }
            .subscribe()
    }

    // 컨트롤러가 호출할 조회 메서드 추가
    fun getNotificationsByUserId(userId: Long): Flux<NotificationResponse> {
        return notificationRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
            .map { it.toResponse() }
    }
}