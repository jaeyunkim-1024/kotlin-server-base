package api.server.base.client.auth.security.model

object AuthRequestDto {
    data class LoginRequestDto(
        var email: String,
        var password: String
    ) {
    }
}