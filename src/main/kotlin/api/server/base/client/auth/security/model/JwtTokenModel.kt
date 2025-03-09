package api.server.base.client.auth.security.model

import java.io.Serializable

data class JwtTokenModel(
    val token:String? = null,
    val exp:Long? = null,
    val iat:Long? = null
)  : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}