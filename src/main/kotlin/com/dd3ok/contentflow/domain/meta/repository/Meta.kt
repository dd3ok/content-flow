package com.dd3ok.contentflow.domain.meta.repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "metas")
data class Meta(
    @Id
    val id: String? = null,
    val name: String,
    val type: MetaType        // TAG, CATEGORY, STYLE 등
)
