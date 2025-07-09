package com.dd3ok.contentflow.common.exception
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        e: Exception,
        exchange: ServerWebExchange
    ): Mono<ResponseEntity<ErrorResponse>> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = e.message,
            path = exchange.request.path.toString()
        )
        return Mono.just(ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR))
    }
}