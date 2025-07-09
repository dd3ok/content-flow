package com.dd3ok.contentflow.domain.meta.controller

import com.dd3ok.contentflow.domain.meta.controller.dto.MetaCreateRequest
import com.dd3ok.contentflow.domain.meta.controller.dto.MetaResponse
import com.dd3ok.contentflow.domain.meta.controller.dto.MetaUpdateRequest
import com.dd3ok.contentflow.domain.meta.repository.MetaType
import com.dd3ok.contentflow.domain.meta.service.MetaService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/metas")
class MetaController(
    private val metaService: MetaService
) {
    @PostMapping
    fun create(@RequestBody request: MetaCreateRequest): Mono<MetaResponse> =
        metaService.create(request)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): Mono<MetaResponse> =
        metaService.getOne(id)

    @GetMapping
    fun getAll(): Flux<MetaResponse> =
        metaService.getAll()

    @GetMapping("/type/{type}")
    fun getByType(@PathVariable type: MetaType): Flux<MetaResponse> =
        metaService.getByType(type)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: MetaUpdateRequest
    ): Mono<MetaResponse> =
        metaService.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): Mono<Void> =
        metaService.delete(id)
}
