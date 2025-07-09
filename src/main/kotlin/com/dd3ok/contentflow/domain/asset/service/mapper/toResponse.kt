package com.dd3ok.contentflow.domain.asset.service.mapper

import com.dd3ok.contentflow.domain.asset.controller.dto.AssetResponse
import com.dd3ok.contentflow.domain.asset.repository.Asset

fun Asset.toResponse(): AssetResponse =
    AssetResponse(
        id = this.id ?: "",
        originalFileName = this.originalFileName,
        url = this.url,
        mimeType = this.mimeType,
        size = this.size
    )