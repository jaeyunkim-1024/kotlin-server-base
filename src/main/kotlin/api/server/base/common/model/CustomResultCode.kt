package api.server.base.common.model

enum class CustomResultCode(val message: String) {
    AUTH_001("Invalid Token"),
    AUTH_002("이메일 중복");
}