package com.dd3ok.contentflow.domain.content.repository

enum class ContentStatus {
    DRAFT,      // 임시저장
    REVIEWING,  // 검토 중
    APPROVED,   // 승인됨
    PUBLISHED,  // 발행됨
    ARCHIVED,   // 보관됨
    REJECTED    // 반려됨
}