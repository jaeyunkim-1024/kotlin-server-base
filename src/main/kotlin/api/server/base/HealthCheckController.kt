package api.server.base

import api.server.base.common.model.CommonResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/health-check")
    fun healthCheck(): ResponseEntity<CommonResponseDto<String>> {
        return ResponseEntity.ok(
            CommonResponseDto()
        )
    }

    @GetMapping("/token/health-check")
    fun tokenHealthCheck(): ResponseEntity<CommonResponseDto<String>> {
        return ResponseEntity.ok(
            CommonResponseDto()
        )
    }
}