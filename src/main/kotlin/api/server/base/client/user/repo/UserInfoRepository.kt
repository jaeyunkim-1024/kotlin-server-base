package api.server.base.client.user.repo

import api.server.base.client.user.entity.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserInfoRepository : JpaRepository<UserInfo,Long>{
    fun findUserInfoByEmail(email: String?): UserInfo
}