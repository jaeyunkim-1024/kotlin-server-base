package api.server.base.common.controller

import api.server.base.common.model.CustomException
import api.server.base.common.model.CustomResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<CustomResponseDto<String>> {
        logger.error("[kbug] handleCustomException")
        return ResponseEntity.ok(
            CustomResponseDto(
                resultCode = e.resultCode.name,
                message = e.resultCode.message,
            )
        )
    }
}