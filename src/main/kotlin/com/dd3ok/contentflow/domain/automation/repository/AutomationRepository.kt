package com.dd3ok.contentflow.domain.automation.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface AutomationRepository : ReactiveMongoRepository<Automation, String> {
    fun findAllByType(type: AutomationType): Flux<Automation>
    fun findAllBySourceContentId(sourceContentId: String): Flux<Automation>
    fun findAllByStatus(status: AutomationStatus): Flux<Automation>
}
