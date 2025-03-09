package api.server.base.client.auth.security

import api.server.base.client.auth.user.entity.UserInfo
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

class CustomUserDetails(
    private var userSeq: Long? = null,
    private var email: String? = null,
    private var userName: String? = null,
    private var password: String? = null,
    private var role: String? = null,
    private var createdAt: LocalDateTime? = null,
    private val isLock: Boolean? = null,

) : UserDetails {
    constructor(userInfo: UserInfo) : this(){
        this.userSeq = userInfo.userSeq!!
        this.email = userInfo.email!!
        this.userName = userInfo.userName!!
        this.password = userInfo.password!!
        this.role = userInfo.userRole!!
        this.createdAt = userInfo.createdAt
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return arrayListOf(SimpleGrantedAuthority(role))
    }

    override fun getPassword(): String {
        return this.password ?: ""
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override fun isAccountNonExpired(): Boolean {
        return super.isAccountNonExpired()
    }

    override fun isAccountNonLocked(): Boolean {
        return super.isAccountNonLocked()
    }

    override fun isCredentialsNonExpired(): Boolean {
        return super.isCredentialsNonExpired()
    }

    override fun isEnabled(): Boolean {
        return super.isEnabled()
    }
}