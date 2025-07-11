package com.dd3ok.contentflow.domain.automation.service

import com.dd3ok.contentflow.domain.automation.controller.dto.AutomationCreateRequest
import com.dd3ok.contentflow.domain.automation.controller.dto.AutomationResponse
import com.dd3ok.contentflow.domain.automation.controller.dto.AutomationUpdateRequest
import com.dd3ok.contentflow.domain.automation.repository.Automation
import com.dd3ok.contentflow.domain.automation.repository.AutomationRepository
import com.dd3ok.contentflow.domain.automation.repository.AutomationType
import com.dd3ok.contentflow.domain.automation.service.event.AutomationCreatedEvent
import com.dd3ok.contentflow.domain.automation.service.mapper.toResponse
import com.dd3ok.contentflow.domain.content.repository.ContentRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class AutomationService(
    private val automationRepository: AutomationRepository,
    private val contentRepository: ContentRepository,
    private val eventPublisher: ApplicationEventPublisher
) {
    fun create(request: AutomationCreateRequest): Mono<AutomationResponse> {
        return contentRepository.findById(request.sourceContentId)
            .switchIfEmpty(Mono.error(RuntimeException("Source content not found: ${request.sourceContentId}")))
            .flatMap { content ->
                val automation = Automation(
                    sourceContentId = request.sourceContentId,
                    authorId = content.authorId,
                    type = request.type
                )
                automationRepository.save(automation)
                    .doOnSuccess { savedAutomation ->
                        val event = AutomationCreatedEvent(
                            automationId = savedAutomation.id!!,
                            sourceContentId = savedAutomation.sourceContentId,
                            sourceContentBody = content.body,
                            authorId = savedAutomation.authorId
                        )
                        eventPublisher.publishEvent(event)
                    }
            }.map { it.toResponse() }
    }

    fun getOne(id: String): Mono<AutomationResponse> =
        automationRepository.findById(id)
            .map { it.toResponse() }

    fun getAll(): Flux<AutomationResponse> =
        automationRepository.findAll().map { it.toResponse() }

    fun getByType(type: AutomationType): Flux<AutomationResponse> =
        automationRepository.findAllByType(type).map { it.toResponse() }

    fun getBySourceContentId(contentId: String): Flux<AutomationResponse> =
        automationRepository.findAllBySourceContentId(contentId).map { it.toResponse() }

    fun update(id: String, request: AutomationUpdateRequest): Mono<AutomationResponse> =
        automationRepository.findById(id).flatMap { existing ->
            val updated = existing.copy(
                status = request.status,
                result = request.result,
                finishedAt = request.finishedAt ?: LocalDateTime.now(),
                errorMessage = request.errorMessage
            )
            automationRepository.save(updated)
        }.map { it.toResponse() }

    fun delete(id: String): Mono<Void> =
        automationRepository.deleteById(id)
}
