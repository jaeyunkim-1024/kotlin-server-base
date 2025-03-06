package api.server.base.client

import org.springframework.data.jpa.repository.JpaRepository

interface UserInfoRepository : JpaRepository<UserInfo,Long>{
}