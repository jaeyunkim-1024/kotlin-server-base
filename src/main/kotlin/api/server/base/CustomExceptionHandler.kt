package api.server.base

import api.server.base.common.model.CommonException
import api.server.base.common.model.CommonResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(CommonException::class)
    fun handleCustomException(e: CommonException): ResponseEntity<CommonResponseDto<String>> {
        logger.error("[kbug] handleCustomException")
        return ResponseEntity.ok(
            CommonResponseDto(
                resultCode = e.resultCode.name,
                message = e.resultCode.message,
            )
        )
    }
}