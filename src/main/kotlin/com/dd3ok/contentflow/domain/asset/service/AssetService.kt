package com.dd3ok.contentflow.domain.asset.service

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.dd3ok.contentflow.domain.asset.controller.dto.AssetResponse
import com.dd3ok.contentflow.domain.asset.repository.Asset
import com.dd3ok.contentflow.domain.asset.repository.AssetRepository
import com.dd3ok.contentflow.domain.asset.service.mapper.toResponse
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class AssetService(
    private val assetRepository: AssetRepository,
    private val cloudinary: Cloudinary
) {
    fun uploadAsset(filePart: FilePart, uploaderId: Long): Mono<AssetResponse> {
        // 1. FilePart의 데이터 스트림을 하나의 바이트 배열로 합칩니다.
        //    Cloudinary SDK는 바이트 배열이나 InputStream을 사용
        return DataBufferUtils.join(filePart.content())
            .flatMap { dataBuffer ->
                val bytes = ByteArray(dataBuffer.readableByteCount())
                dataBuffer.read(bytes)
                DataBufferUtils.release(dataBuffer)

                // 2. Cloudinary SDK의 upload 메소드는 블로킹(Blocking) 동작을 합니다.
                Mono.fromCallable {
                    // Cloudinary에 업로드하기 위한 옵션을 설정합니다.
                    // public_id를 지정하지 않으면 랜덤한 문자열로 생성됩니다.
                    // resource_type을 "auto"로 설정하면 이미지, 비디오, raw 파일 등을 자동으로 감지합니다.
                    val options = ObjectUtils.asMap(
                        "resource_type", "auto",
                        "public_id", filePart.filename() // 원본 파일명으로 public_id 설정
                    )

                    // 실제 업로드 실행
                    cloudinary.uploader().upload(bytes, options)
                }
                    .subscribeOn(Schedulers.boundedElastic()) // 블로킹 작업을 위한 전용 스레드 풀에서 실행
                    .flatMap { uploadResult ->
                        // 3. Cloudinary로부터 받은 업로드 결과를 DB에 저장합니다.
                        assetRepository.save(
                            Asset(
                                originalFileName = filePart.filename(),
                                // Cloudinary는 파일 확장자를 자동으로 처리하므로 storedFileName이 필요 없을 수 있습니다.
                                // 여기서는 Cloudinary의 public_id를 저장합니다.
                                storedFileName = uploadResult["public_id"] as String,
                                url = uploadResult["secure_url"] as String, // HTTPS를 지원하는 고유 URL
                                mimeType = filePart.headers().contentType?.toString(),
                                size = (uploadResult["bytes"] as Int).toLong(),
                                uploaderId = uploaderId
                            )
                        )
                    }
            }
            .map { it.toResponse() }
            .doOnError {
                // 에러 로깅 (실제 서비스에서는 로깅 프레임워크 사용)
                println("Cloudinary upload failed: ${it.message}")
            }
    }
}