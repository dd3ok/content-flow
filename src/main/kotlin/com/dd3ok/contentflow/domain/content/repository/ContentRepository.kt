package com.dd3ok.contentflow.domain.content.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ContentRepository : ReactiveMongoRepository<Content, String> {
    fun findAllByAuthorId(authorId: Long): Flux<Content>
    fun findAllByStatus(status: ContentStatus): Flux<Content>
}
