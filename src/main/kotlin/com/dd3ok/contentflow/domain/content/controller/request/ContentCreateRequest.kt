package com.dd3ok.contentflow.domain.content.controller.request

data class ContentCreateRequest(
    val title: String,
    val body: String,
    val authorId: Long,
    val tags: List<String> = emptyList()
)