package com.dd3ok.contentflow.domain.content.controller.dto

data class ContentCreateRequest(
    val title: String,
    val body: String,
    val authorId: Long,
    val tags: List<String> = emptyList()
)