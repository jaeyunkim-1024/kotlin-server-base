package api.server.base.admin.user.repo

import api.server.base.admin.user.entity.LoginHistory
import org.springframework.data.jpa.repository.JpaRepository

interface LoginHistoryRepository : JpaRepository<LoginHistory,Long> {
}