package com.dd3ok.contentflow.domain.asset.controller

import com.dd3ok.contentflow.domain.asset.controller.dto.AssetResponse
import com.dd3ok.contentflow.domain.asset.service.AssetService
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/assets")
class AssetController(private val assetService: AssetService) {

    @PostMapping("/upload")
    fun upload(@RequestPart("file") filePart: FilePart): Mono<AssetResponse> {
        // TODO: 현재는 uploaderId를 1L로 하드코딩. 추후 인증 기능 구현 후 실제 사용자 ID로 대체 필요
        val uploaderId = 1L
        return assetService.uploadAsset(filePart, uploaderId)
    }
}