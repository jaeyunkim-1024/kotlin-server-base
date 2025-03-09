package api.server.base.client.auth.security.model

data class JwtTokenModel(
    private val token:String,
    private val exp:Long,
    private val iat:Long
) {
}