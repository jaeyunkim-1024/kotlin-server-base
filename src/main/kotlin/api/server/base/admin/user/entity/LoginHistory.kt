package api.server.base.admin.user.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime

@Entity
@Table(name = "LOGIN_HISTORY")
open class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOGIN_HISTORY_SEQ", nullable = false)
    open var id: Int? = null

    @Column(name = "USER_SEQ", nullable = false)
    open var userSeq: Long? = null

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "LOGIN_ACCESS_TIME")
    open var loginAccessTime: LocalDateTime? = null

    @Column(name = "ACCESS_CD", nullable = false, length = 45)
    open var accessCd: String? = null
}