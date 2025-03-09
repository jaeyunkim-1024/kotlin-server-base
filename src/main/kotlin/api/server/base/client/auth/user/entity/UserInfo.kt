package api.server.base.client.auth.user.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime

@Entity
@Table(name = "USER_INFO")
class UserInfo(
    @Column(name = "EMAIL", nullable = false, length = 200)
    var email: String? = null,

    @Column(name = "PASSWORD", nullable = false, length = 1000)
    var password: String? = null,

    @Column(name = "USER_NAME", length = 200)
    var userName: String? = null,

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT")
    var createdAt: LocalDateTime? = null,

    @Column(name = "UPDATED_AT")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "EXPIRE_AT")
    var expireAt: LocalDateTime? = null,

    @ColumnDefault("0")
    @Column(name = "IS_LOCK", nullable = false)
    var isLock: Boolean? = false,

    @ColumnDefault("'NO_CERT'")
    @Column(name = "USER_ROLE", length = 45)
    var userRole: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ", nullable = false)
    var userSeq: Long? = null
}