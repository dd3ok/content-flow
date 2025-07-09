package com.dd3ok.contentflow.domain.content.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ContentRepository : ReactiveMongoRepository<Content, String>
