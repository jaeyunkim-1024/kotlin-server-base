package api.server.base.admin.mappers

import api.server.base.admin.LoginHistory

interface LoginHistoryMapper {
    fun selectLoginHistory(): List<LoginHistory>
}