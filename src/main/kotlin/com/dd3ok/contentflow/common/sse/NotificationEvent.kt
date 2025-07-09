package com.dd3ok.contentflow.common.sse

data class NotificationEvent(
    val eventName: String,
    val data: Any
)