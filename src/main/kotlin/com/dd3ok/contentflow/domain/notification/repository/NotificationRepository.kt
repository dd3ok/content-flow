package com.dd3ok.contentflow.domain.notification.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface NotificationRepository : ReactiveMongoRepository<Notification, String> {
    fun findAllByUserIdOrderByCreatedAtDesc(userId: Long): Flux<Notification>
}