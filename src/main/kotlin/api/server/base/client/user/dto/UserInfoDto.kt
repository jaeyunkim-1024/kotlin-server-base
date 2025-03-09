package api.server.base.client.user.dto

import api.server.base.client.user.entity.UserInfo
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
