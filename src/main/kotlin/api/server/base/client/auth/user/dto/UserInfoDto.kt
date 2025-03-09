package api.server.base.client.auth.user.dto

import api.server.base.client.auth.user.entity.UserInfo
import java.time.LocalDateTime

data class UserInfoDto(
    var email: String? = null,
    var userName: String? = null,
    var createdAt: LocalDateTime? = null
) {
    constructor(entity: UserInfo): this() {
        email = entity.email
        userName = entity.userName
        createdAt = entity.createdAt
    }
}
