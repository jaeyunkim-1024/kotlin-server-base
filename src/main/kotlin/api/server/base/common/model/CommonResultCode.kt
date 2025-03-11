package api.server.base.common.model

enum class CommonResultCode(val message: String) {
    AUTH_001("유효하지 않은 토큰 입니다."),
    AUTH_002("이메일 중복"),
    AUTH_003("이메일 혹은 비밀번호를 확인해주세요.")
}