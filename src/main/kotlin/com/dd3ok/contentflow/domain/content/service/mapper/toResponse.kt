package com.dd3ok.contentflow.domain.content.service.mapper

import com.dd3ok.contentflow.domain.content.repository.Content
import com.dd3ok.contentflow.domain.content.controller.response.ContentResponse

fun Content.toResponse(): ContentResponse =
    ContentResponse(
        id = this.id ?: "",
        title = this.title,
        body = this.body,
        authorId = this.authorId,
        status = this.status,
        tags = this.tags,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        version = this.version
    )