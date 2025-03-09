package api.server.base.client.auth.security.provider

import api.server.base.client.auth.security.model.CustomUserDetails
import api.server.base.client.auth.security.model.JwtTokenModel
import api.server.base.client.auth.user.enums.UserRoles
import api.server.base.client.auth.user.service.UserTokenService
import api.server.base.common.enums.DotEnvScheme
import api.server.base.common.enums.RedisKeyGen
import api.server.base.common.model.CustomException
import api.server.base.common.model.CustomResultCode
import api.server.base.common.utils.CustomTimeUtils
import com.fasterxml.jackson.core.JsonProcessingException
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.io.IOException
import java.sql.Timestamp
import java.util.stream.Collectors
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    private val userTokenService: UserTokenService
) {
    /** 토큰 생성 */
    @Throws(JsonProcessingException::class)
    fun issuedToken(authentication: Authentication): JwtTokenModel {
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }.collect(Collectors.joining(","))
        val principal: CustomUserDetails = authentication.principal as CustomUserDetails
        return tokenBuilder(authentication.name, principal, authorities)
    }

    @Throws(JsonProcessingException::class)
    fun reIssuedToken(subject: String, principal: CustomUserDetails, authorities: String): JwtTokenModel {
        return tokenBuilder(subject, principal, authorities)
    }

    private fun tokenBuilder(subject: String, principal: CustomUserDetails, authorities: String): JwtTokenModel {
        val issuedAt: Timestamp = CustomTimeUtils.getCurrentTime()
        val expirationInt = RedisKeyGen.USER_TOKEN.ttl()
        val expiration: Timestamp = CustomTimeUtils.getExpireTimeByTime(expirationInt)

        val jwt = Jwts.builder()
            .subject(subject)
            .claim("authorities", authorities)
            .claim("principal", principal.username)
            .claim("isEmailCert", if (principal.isEmailCert()) "Y" else "N")
            .claim("isLock", if (principal.isAccountLocked()) "Y" else "N")
            .issuedAt(issuedAt)
            .expiration(expiration)
            .signWith(getKey(), Jwts.SIG.HS256)
            .compact()
        val saltingToken = salting(jwt)

        userTokenService.setEnableToken(saltingToken, principal.username)
        return JwtTokenModel(
            token = saltingToken,
            iat = issuedAt.time,
            exp = expiration.time
        )
    }

    /** 토큰 만료 */
    fun tokenExpire(email: String?): Boolean {
        try {
            userTokenService.setDisableToken(email)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    /** 토큰 추출 */
    @Throws(IOException::class)
    fun getAuthentication(token: String?): Authentication {
        val claims = getClaimsFromToken(token)
        val email = claims.subject
        val authorities = claims.get("authorities", String::class.java)
        return UsernamePasswordAuthenticationToken(email, "", arrayListOf(SimpleGrantedAuthority(authorities)))
    }

    /** 토큰 가져오기 */
    fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader("Authorization")
        if(!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ")){
            throw CustomException(CustomResultCode.AUTH_001)
        }
        val saltingToken = bearerToken.substring(7)
        return desalting(saltingToken)
    }

    /** 토큰 검증 */
    fun validateToken(token: String): Boolean {
        val saltingToken = salting(token)
        val isExpireInRedis = userTokenService.isEnableToken(saltingToken)
        if (!isExpireInRedis) {
            return false
        }
        try {
            val claims = getClaimsFromToken(token)
            val now: Timestamp = CustomTimeUtils.getCurrentTime()
            val isLock = claims.get("isLock", String::class.java) == "Y"
            val issuedAt = claims.issuedAt.time

            val isExpire = claims.expiration.before(now)
            val isEnable = now.time > issuedAt
            return !isLock && !isExpire && isEnable
        } catch (e: SecurityException) {
            throw JwtException(e.javaClass.name)
        } catch (e: MalformedJwtException) {
            throw JwtException(e.javaClass.name)
        } catch (e: ExpiredJwtException) {
            throw JwtException(e.javaClass.name)
        } catch (e: UnsupportedJwtException) {
            throw JwtException(e.javaClass.name)
        } catch (e: IllegalArgumentException) {
            throw JwtException(e.javaClass.name)
        } catch (e: Exception) {
            throw JwtException(e.message)
        }
    }

    fun isCertUser(request: HttpServletRequest): Boolean {
        val token = resolveToken(request)
        val claims = getClaimsFromToken(token)
        val isEmailCert = claims.get("isEmailCert", String::class.java)
        val authorities = claims.get("authorities", String::class.java)
        return isEmailCert == "Y" && authorities != UserRoles.NO_CERT.role()
    }

    /** Principal(=email=subject) 가져오기 */
    fun getPrincipal(request: HttpServletRequest): String {
        val token = resolveToken(request)
        val claims = getClaimsFromToken(token)
        return claims.subject
    }

    private fun getKey(): SecretKey {
        val secretKey: String = DotEnvScheme.JWT_SECRETKEY.toString()
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    private fun getClaimsFromToken(token: String?): Claims {
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun salting(jwt: String): String {
        val num: Int = DotEnvScheme.JWT_NUM.toString().toInt()
        val salt: String = DotEnvScheme.JWT_SALT.toString()

        val arr = jwt.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val stringBuilder = StringBuilder()
        val header = arr[0]
        val payload = arr[1]
        val saltingPayload = payload.substring(0, num) + salt + payload.substring(num)
        val sign = arr[2]
        stringBuilder.append(header)
        stringBuilder.append(".")
        stringBuilder.append(saltingPayload)
        stringBuilder.append(".")
        stringBuilder.append(sign)
        return stringBuilder.toString()
    }

    private fun desalting(token: String): String {
        val num: Int = DotEnvScheme.JWT_NUM.toString().toInt()
        val salt: String = DotEnvScheme.JWT_SALT.toString()

        val arr = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val stringBuilder = StringBuilder()
        val header = arr[0]
        val saltingPayload = arr[1]
        val payload = saltingPayload.substring(0, num) + saltingPayload.substring(num + salt.length)
        val sign = arr[2]
        stringBuilder.append(header)
        stringBuilder.append(".")
        stringBuilder.append(payload)
        stringBuilder.append(".")
        stringBuilder.append(sign)
        return stringBuilder.toString()
    }
}