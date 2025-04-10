package example.magiclinklogin

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class MagikLinkUserDetailsService : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return MagicLinkUserDetails(
            username = username,
            password = null,
            authorities = emptyList()
        )
    }
}

data class MagicLinkUserDetails(
    private val username: String,
    private val password: String?,
    private val authorities: List<GrantedAuthority>,
) : UserDetails {
    override fun getUsername(): String = username

    override fun getPassword(): String? = null

    override fun getAuthorities(): List<GrantedAuthority> = authorities
}
