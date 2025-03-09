package api.server.base.client.mappers

import api.server.base.client.auth.user.entity.UserInfo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserInfoMapper {
    fun selectUserInfo(): List<UserInfo>
}