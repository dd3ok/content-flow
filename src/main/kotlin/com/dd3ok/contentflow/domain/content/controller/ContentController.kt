package com.dd3ok.contentflow.domain.content.controller

import com.dd3ok.contentflow.domain.content.controller.dto.ContentCreateRequest
import com.dd3ok.contentflow.domain.content.controller.dto.ContentResponse
import com.dd3ok.contentflow.domain.content.controller.dto.ContentUpdateRequest
import com.dd3ok.contentflow.domain.content.repository.ContentStatus
import com.dd3ok.contentflow.domain.content.service.ContentService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/contents")
class ContentController(
    private val contentService: ContentService
) {
    @PostMapping
    fun create(@RequestBody request: ContentCreateRequest): Mono<ContentResponse> =
        contentService.createContent(request)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): Mono<ContentResponse> =
        contentService.getContent(id)

    @GetMapping
    fun getAll(): Flux<ContentResponse> =
        contentService.getAllContents()

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: ContentUpdateRequest
    ): Mono<ContentResponse> =
        contentService.updateContent(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): Mono<Void> =
        contentService.deleteContent(id)

    @GetMapping("/author/{authorId}")
    fun getByAuthorId(@PathVariable authorId: Long): Flux<ContentResponse> =
        contentService.getContentsByAuthor(authorId)

    @GetMapping("/status/{status}")
    fun getByStatus(@PathVariable status: ContentStatus): Flux<ContentResponse> =
        contentService.getContentsByStatus(status)
}