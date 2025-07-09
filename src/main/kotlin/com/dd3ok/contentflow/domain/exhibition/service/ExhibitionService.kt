package com.dd3ok.contentflow.domain.exhibition.service

import com.dd3ok.contentflow.domain.exhibition.controller.request.ExhibitionCreateRequest
import com.dd3ok.contentflow.domain.exhibition.controller.request.ExhibitionUpdateRequest
import com.dd3ok.contentflow.domain.exhibition.controller.response.ExhibitionResponse
import com.dd3ok.contentflow.domain.exhibition.repository.Exhibition
import com.dd3ok.contentflow.domain.exhibition.repository.ExhibitionChannel
import com.dd3ok.contentflow.domain.exhibition.repository.ExhibitionRepository
import com.dd3ok.contentflow.domain.exhibition.service.mapper.toResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ExhibitionService(
    private val exhibitionRepository: ExhibitionRepository
) {
    fun create(request: ExhibitionCreateRequest): Mono<ExhibitionResponse> {
        val exhibition = Exhibition(
            contentId = request.contentId,
            displayOrder = request.displayOrder,
            channel = request.channel,
            exposureStart = request.exposureStart,
            exposureEnd = request.exposureEnd
        )
        return exhibitionRepository.save(exhibition).map { it.toResponse() }
    }

    fun getOne(id: String): Mono<ExhibitionResponse> =
        exhibitionRepository.findById(id)
            .map { it.toResponse() }

    fun getAll(): Flux<ExhibitionResponse> =
        exhibitionRepository.findAll().map { it.toResponse() }

    fun getByChannel(channel: ExhibitionChannel): Flux<ExhibitionResponse> =
        exhibitionRepository.findAllByChannel(channel)
            .map { it.toResponse() }

    fun update(id: String, request: ExhibitionUpdateRequest): Mono<ExhibitionResponse> =
        exhibitionRepository.findById(id)
            .flatMap { existing ->
                val updated = existing.copy(
                    displayOrder = request.displayOrder ?: existing.displayOrder,
                    isVisible = request.isVisible ?: existing.isVisible,
                    channel = request.channel ?: existing.channel,
                    exposureStart = request.exposureStart ?: existing.exposureStart,
                    exposureEnd = request.exposureEnd ?: existing.exposureEnd
                )
                exhibitionRepository.save(updated)
            }.map { it.toResponse() }

    fun delete(id: String): Mono<Void> =
        exhibitionRepository.deleteById(id)
}