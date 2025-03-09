package api.server.base.client.auth.security.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

object AuthRequestDto {
    data class SignUpRequestDto(
        @NotNull @NotBlank @Email
        var email: String,
        @NotNull @NotBlank
        var password: String,
        @NotNull @NotBlank
        var userName: String
    )
    data class LoginRequestDto(
        var email: String? = null,
        var password: String? = null
    )
}