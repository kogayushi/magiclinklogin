package example.magiclinklogin

import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ott.InMemoryOneTimeTokenService
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationProvider
import org.springframework.security.authentication.ott.OneTimeTokenService
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .oneTimeTokenLogin {
                it
                    .authenticationProvider(oneTimeTokenAuthenticationProvider())
                    .authenticationSuccessHandler(
                        ForwardAuthenticationSuccessHandler("/ott/sent")
                    )
                    .authenticationFailureHandler { _, response, _ ->
                        response.status = HttpServletResponse.SC_UNAUTHORIZED
                    }
            }
            .formLogin {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }
            .csrf { it.disable() }
        return http.build()
    }

    @Bean
    fun oneTimeTokenAuthenticationProvider(): OneTimeTokenAuthenticationProvider {
        return OneTimeTokenAuthenticationProvider(
            inMemoryOneTimeTokenService(),
            MagikLinkUserDetailsService()
        )
    }

    @Bean
    fun inMemoryOneTimeTokenService(): OneTimeTokenService = InMemoryOneTimeTokenService()
}
