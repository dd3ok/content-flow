package com.dd3ok.contentflow.domain.asset.controller.dto

data class AssetResponse(
    val id: String,
    val originalFileName: String,
    val url: String,
    val mimeType: String?,
    val size: Long
)