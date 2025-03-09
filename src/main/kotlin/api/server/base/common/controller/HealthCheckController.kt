package api.server.base.common.controller

import api.server.base.common.model.CustomResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/health-check")
    fun healthCheck(): ResponseEntity<CustomResponseDto<String>> {
        return ResponseEntity.ok(
            CustomResponseDto()
        )
    }

    @GetMapping("/token/health-check")
    fun tokenHealthCheck(): ResponseEntity<CustomResponseDto<String>> {
        return ResponseEntity.ok(
            CustomResponseDto()
        )
    }
}