package api.server.base.client

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime

@Entity
@Table(name = "USER_INFO")
open class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ", nullable = false)
    open var id: Int? = null

    @Column(name = "EMAIL", nullable = false, length = 200)
    open var email: String? = null

    @Column(name = "PASSWORD", nullable = false, length = 1000)
    open var password: String? = null

    @Column(name = "USER_NAME", length = 200)
    open var userName: String? = null

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT")
    open var createdAt: LocalDateTime? = null

    @Column(name = "UPDATED_AT")
    open var updatedAt: LocalDateTime? = null

    @Column(name = "EXPIRE_AT")
    open var expireAt: LocalDateTime? = null

    @ColumnDefault("0")
    @Column(name = "IS_LOCK", nullable = false)
    open var isLock: Int? = null

    @ColumnDefault("'NO_CERT'")
    @Column(name = "USER_ROLE", length = 45)
    open var userRole: String? = null
}