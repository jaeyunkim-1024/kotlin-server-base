package api.server.base.common.model

enum class CustomResultCode(val message: String) {
    AUTH_001("Invalid Token"),
    AUTH_002("이메일 중복"),
    AUTH_003("이메일 혹은 비밀번호를 확인해주세요.")
}