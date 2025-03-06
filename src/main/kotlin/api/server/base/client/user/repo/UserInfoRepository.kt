package api.server.base.client.user.repo

import api.server.base.client.user.entity.UserInfo
import org.springframework.data.jpa.repository.JpaRepository

interface UserInfoRepository : JpaRepository<UserInfo,Long>{
}