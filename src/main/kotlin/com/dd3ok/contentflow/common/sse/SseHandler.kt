package com.dd3ok.contentflow.common.sse

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.Many
import java.util.concurrent.ConcurrentHashMap

@Component
class SseHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    // 각 사용자 ID별로 이벤트 스트림(Sink)을 관리
    private val sinks: MutableMap<String, Many<NotificationEvent>> = ConcurrentHashMap()

    fun subscribe(id: String): Flux<NotificationEvent> {
        val sink = Sinks.many().multicast().onBackpressureBuffer<NotificationEvent>()
        sinks[id] = sink
        logger.info("SSE subscribed: {}", id)

        return sink.asFlux().doOnCancel {
            sinks.remove(id)
            logger.info("SSE unsubscribed and removed: {}", id)
        }
    }

    fun send(id: String, event: NotificationEvent) {
        sinks[id]?.tryEmitNext(event)
    }
}