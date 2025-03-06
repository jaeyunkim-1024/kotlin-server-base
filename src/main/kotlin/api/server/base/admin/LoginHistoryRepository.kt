package api.server.base.admin

import org.springframework.data.jpa.repository.JpaRepository

interface LoginHistoryRepository : JpaRepository<LoginHistory,Long> {
}