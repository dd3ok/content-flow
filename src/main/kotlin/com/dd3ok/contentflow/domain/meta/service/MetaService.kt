package com.dd3ok.contentflow.domain.meta.service

import com.dd3ok.contentflow.domain.meta.controller.dto.MetaCreateRequest
import com.dd3ok.contentflow.domain.meta.controller.dto.MetaResponse
import com.dd3ok.contentflow.domain.meta.controller.dto.MetaUpdateRequest
import com.dd3ok.contentflow.domain.meta.repository.Meta
import com.dd3ok.contentflow.domain.meta.repository.MetaRepository
import com.dd3ok.contentflow.domain.meta.repository.MetaType
import com.dd3ok.contentflow.domain.meta.service.mapper.toResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MetaService(
    private val metaRepository: MetaRepository
) {
    fun create(request: MetaCreateRequest): Mono<MetaResponse> {
        val meta = Meta(
            name = request.name,
            type = request.type
        )
        return metaRepository.save(meta).map { it.toResponse() }
    }

    fun getOne(id: String): Mono<MetaResponse> =
        metaRepository.findById(id)
            .map { it.toResponse() }

    fun getAll(): Flux<MetaResponse> =
        metaRepository.findAll().map { it.toResponse() }

    fun getByType(type: MetaType): Flux<MetaResponse> =
        metaRepository.findAllByType(type)
            .map { it.toResponse() }

    fun update(id: String, request: MetaUpdateRequest): Mono<MetaResponse> =
        metaRepository.findById(id)
            .flatMap { existing ->
                val updated = existing.copy(
                    name = request.name ?: existing.name,
                    type = request.type ?: existing.type
                )
                metaRepository.save(updated)
            }.map { it.toResponse() }

    fun delete(id: String): Mono<Void> =
        metaRepository.deleteById(id)
}
