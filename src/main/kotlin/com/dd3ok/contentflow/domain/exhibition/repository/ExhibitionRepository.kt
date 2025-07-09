package com.dd3ok.contentflow.domain.exhibition.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ExhibitionRepository : ReactiveMongoRepository<Exhibition, String> {
    fun findAllByChannel(channel: ExhibitionChannel): Flux<Exhibition>
}