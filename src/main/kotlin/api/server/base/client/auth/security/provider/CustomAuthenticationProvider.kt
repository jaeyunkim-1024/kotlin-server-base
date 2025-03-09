package api.server.base.client.auth.security.provider

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails

class CustomAuthenticationProvider(
) : DaoAuthenticationProvider() {
    @Throws(AuthenticationException::class)
    override fun additionalAuthenticationChecks(
        userDetails: UserDetails,
        authentication: UsernamePasswordAuthenticationToken
    ) {
        val presentedPassword = authentication.credentials.toString()
        if (!passwordEncoder.matches(presentedPassword, userDetails.password)) {
            throw BadCredentialsException(
                messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    userDetails.username
                )
            )
        }
    }
}