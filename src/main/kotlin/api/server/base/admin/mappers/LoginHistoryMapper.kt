package api.server.base.admin.mappers

import api.server.base.admin.user.entity.LoginHistory

interface LoginHistoryMapper {
    fun selectLoginHistory(): List<LoginHistory>
}