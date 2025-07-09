package com.dd3ok.contentflow.domain.asset.service

import com.dd3ok.contentflow.domain.asset.controller.dto.AssetResponse
import com.dd3ok.contentflow.domain.asset.repository.Asset
import com.dd3ok.contentflow.domain.asset.repository.AssetRepository
import com.dd3ok.contentflow.domain.asset.service.mapper.toResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.nio.file.Paths
import java.util.UUID

@Service
class AssetService(
    private val assetRepository: AssetRepository,
    @Value("\${file.upload-dir}") private val uploadDir: String
) {
    fun uploadAsset(filePart: FilePart, uploaderId: Long): Mono<AssetResponse> {
        val storedFileName = "${UUID.randomUUID()}-${filePart.filename()}"
        val path = Paths.get(uploadDir, storedFileName)
        val url = "/assets/$storedFileName" // 예시 URL, 실제로는 CDN 주소 등이 될 수 있음

        // 파일 크기를 얻기 위한 Mono
        val sizeMono = DataBufferUtils.join(filePart.content())
            .map { dataBuffer ->
                val size = dataBuffer.readableByteCount().toLong()
                DataBufferUtils.release(dataBuffer)
                size
            }

        // 파일을 저장하고, 그 다음 DB에 메타데이터를 저장
        return filePart.transferTo(path).then(
            sizeMono.flatMap { size ->
                assetRepository.save(
                    Asset(
                        originalFileName = filePart.filename(),
                        storedFileName = storedFileName,
                        url = url,
                        mimeType = filePart.headers().contentType?.toString(),
                        size = size,
                        uploaderId = uploaderId
                    )
                ).map { it.toResponse() }
            }
        )
    }
}