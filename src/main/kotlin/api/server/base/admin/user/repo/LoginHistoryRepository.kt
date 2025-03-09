package api.server.base.admin.user.repo

import api.server.base.admin.user.entity.LoginHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoginHistoryRepository : JpaRepository<LoginHistory,Long> {
}