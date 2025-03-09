package api.server.base.client.auth.user.enums

enum class AccessCode(val code:String, val desc:String) {
    LOGIN_SUCCESS("10","로그인 성공"),
    LOGIN_FAILED_NOT_CORRECT_EMAIL_PWD("20","이메일 혹은 비밀번호가 잘못되었습니다."),
    LOGIN_FAILED_INVALID_PASSWORD("30","비밀번호는 최소 6자, 최대 20자를 만족해야합니다."),
    LOGOUT("40","로그아웃 성공"),
}