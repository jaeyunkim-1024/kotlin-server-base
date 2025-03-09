package api.server.base.client.auth.user.dto

import api.server.base.client.auth.user.entity.UserInfo
import java.time.LocalDateTime

data class UserInfoDto(
    private var email: String? = null,
    private var userName: String? = null,
    private var createdAt: LocalDateTime? = null
) {
    constructor(entity: UserInfo): this() {
        email = entity.email
        userName = entity.userName
        createdAt = entity.createdAt
    }
}
