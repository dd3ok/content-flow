package com.dd3ok.contentflow.domain.exhibition.controller

import com.dd3ok.contentflow.domain.exhibition.controller.request.ExhibitionCreateRequest
import com.dd3ok.contentflow.domain.exhibition.controller.request.ExhibitionUpdateRequest
import com.dd3ok.contentflow.domain.exhibition.controller.response.ExhibitionResponse
import com.dd3ok.contentflow.domain.exhibition.repository.ExhibitionChannel
import com.dd3ok.contentflow.domain.exhibition.service.ExhibitionService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/exhibitions")
class ExhibitionController(
    private val exhibitionService: ExhibitionService
) {
    @PostMapping
    fun create(@RequestBody request: ExhibitionCreateRequest): Mono<ExhibitionResponse> =
        exhibitionService.create(request)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): Mono<ExhibitionResponse> =
        exhibitionService.getOne(id)

    @GetMapping
    fun getAll(): Flux<ExhibitionResponse> =
        exhibitionService.getAll()

    @GetMapping("/channel/{channel}")
    fun getByChannel(@PathVariable channel: ExhibitionChannel): Flux<ExhibitionResponse> =
        exhibitionService.getByChannel(channel)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: ExhibitionUpdateRequest
    ): Mono<ExhibitionResponse> =
        exhibitionService.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): Mono<Void> =
        exhibitionService.delete(id)
}
