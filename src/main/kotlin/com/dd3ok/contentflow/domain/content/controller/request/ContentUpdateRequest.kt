package com.dd3ok.contentflow.domain.content.controller.request

data class ContentUpdateRequest(
    val title: String?,
    val body: String?,
    val tags: List<String>?
)