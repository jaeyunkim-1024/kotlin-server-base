package api.server.base.common.enums

object SecurityPaths {
    final val allowOrigin = listOf(
        "127.0.0.1:8080",
        "localhost:3000",
        "localhost:8080",
    )

    final val adminPath:String = "/api/admin/**"
    final val clientPath:String = "/api/**"

    final val allowPermitAllPaths = listOf(
        "/api/auth/sign-in",
        "/api/auth/sign-up",
        "/health-check"
    )
    final val allowNoCertUserPaths = listOf(
        "api/auth/email/**"
    )
}