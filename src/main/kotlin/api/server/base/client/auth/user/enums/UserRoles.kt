package api.server.base.client.auth.user.enums

enum class UserRoles(val code: String) {
    NO_CERT("ROLE_NO_CERT"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    val role: () -> String = {  code.replace("ROLE_","") }
}