package com.dd3ok.contentflow.domain.content.service

import com.dd3ok.contentflow.domain.content.controller.dto.ContentCreateRequest
import com.dd3ok.contentflow.domain.content.controller.dto.ContentUpdateRequest
import com.dd3ok.contentflow.domain.content.controller.dto.ContentResponse
import com.dd3ok.contentflow.domain.content.repository.Content
import com.dd3ok.contentflow.domain.content.repository.ContentRepository
import com.dd3ok.contentflow.domain.content.repository.ContentStatus
import com.dd3ok.contentflow.domain.content.service.mapper.toResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class ContentService(
    private val contentRepository: ContentRepository
) {
    fun createContent(request: ContentCreateRequest): Mono<ContentResponse> {
        val content = Content(
            title = request.title,
            body = request.body,
            authorId = request.authorId,
            tags = request.tags,
            status = ContentStatus.DRAFT,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            version = 1
        )
        return contentRepository.save(content).map { it.toResponse() }
    }

    fun getContent(id: String): Mono<ContentResponse> =
        contentRepository.findById(id)
            .map { it.toResponse() }

    fun getAllContents(): Flux<ContentResponse> =
        contentRepository.findAll().map { it.toResponse() }

    fun updateContent(id: String, request: ContentUpdateRequest): Mono<ContentResponse> =
        contentRepository.findById(id)
            .flatMap { existing ->
                val updated = existing.copy(
                    title = request.title ?: existing.title,
                    body = request.body ?: existing.body,
                    tags = request.tags ?: existing.tags,
                    updatedAt = LocalDateTime.now(),
                    version = existing.version + 1
                )
                contentRepository.save(updated)
            }.map { it.toResponse() }

    fun deleteContent(id: String): Mono<Void> =
        contentRepository.deleteById(id)

    fun getContentsByAuthor(authorId: Long): Flux<ContentResponse> =
        contentRepository.findAllByAuthorId(authorId)
            .map { it.toResponse() }

    fun getContentsByStatus(status: ContentStatus): Flux<ContentResponse> =
        contentRepository.findAllByStatus(status)
            .map { it.toResponse() }

}