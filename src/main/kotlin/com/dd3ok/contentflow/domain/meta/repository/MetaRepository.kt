package com.dd3ok.contentflow.domain.meta.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface MetaRepository : ReactiveMongoRepository<Meta, String> {
    fun findAllByType(type: MetaType): Flux<Meta>
    fun findByNameAndType(name: String, type: MetaType): Flux<Meta>
}
