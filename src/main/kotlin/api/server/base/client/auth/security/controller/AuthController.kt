package api.server.base.client.auth.security.controller

import api.server.base.client.auth.security.model.AuthRequestDto
import api.server.base.client.auth.security.model.JwtTokenModel
import api.server.base.client.auth.security.provider.JwtTokenProvider
import api.server.base.client.auth.security.service.AuthService
import api.server.base.common.model.CustomResponseDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody dto: AuthRequestDto.SignUpRequestDto) : ResponseEntity<CustomResponseDto<JwtTokenModel>> {
        val authentication = authService.signUp(dto)
        val token = jwtTokenProvider.issuedToken(authentication)
        return ResponseEntity.ok(
            CustomResponseDto(
                data = token
            )
        )
    }
}