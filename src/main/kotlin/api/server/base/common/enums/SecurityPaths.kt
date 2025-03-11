package api.server.base.common.enums

object SecurityPaths {
    val allowOrigin = listOf(
        "127.0.0.1:8080",
        "localhost:3000",
    )

    val adminPath:String = "/api/admin/**"

    val allowPermitAllPaths = listOf(
        "/api/auth/sign-in",
        "/api/auth/sign-up",
        "/health-check",
    )
    val allowNoCertUserPaths = listOf(
        "api/auth/email/**"
    )
}