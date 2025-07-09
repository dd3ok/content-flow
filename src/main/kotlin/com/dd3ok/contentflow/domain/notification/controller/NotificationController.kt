package com.dd3ok.contentflow.domain.notification.controller

import com.dd3ok.contentflow.common.sse.SseHandler
import com.dd3ok.contentflow.domain.notification.controller.dto.NotificationResponse
import com.dd3ok.contentflow.domain.notification.service.NotificationService
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration

@RestController
class NotificationController(
    private val notificationService: NotificationService,
    private val sseHandler: SseHandler
) {
    // 리액티브 방식으로 SSE 구독 API 수정
    @GetMapping("/api/v1/notifications/subscribe/{userId}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(@PathVariable userId: Long): Flux<ServerSentEvent<Any>> {
        val stream = sseHandler.subscribe(userId.toString())
            .map { event ->
                ServerSentEvent.builder<Any>()
                    .event(event.eventName)
                    .data(event.data)
                    .build()
            }
        // 연결 유지 및 타임아웃 방지를 위한 heartbeat
        val heartbeat = Flux.interval(Duration.ofSeconds(15))
            .map { ServerSentEvent.builder<Any>().comment("heartbeat").build() }

        return Flux.merge(stream, heartbeat)
    }

    // 서비스 계층을 통해 알림 내역 조회
    @GetMapping("/api/v1/notifications/{userId}")
    fun getNotifications(@PathVariable userId: Long): Flux<NotificationResponse> {
        return notificationService.getNotificationsByUserId(userId)
    }
}