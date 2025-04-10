package example.magiclinklogin

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.authentication.ott.OneTimeToken
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler
import org.springframework.security.web.util.UrlUtils
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class MagicLinkOneTimeTokenGenerationSuccessHandler(
    private val mailSender: MailSender,
) : OneTimeTokenGenerationSuccessHandler {

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, oneTimeToken: OneTimeToken) {
        val builder = UriComponentsBuilder.fromUriString(UrlUtils.buildFullRequestUrl(request))
            .replacePath(request.contextPath)
            .replaceQuery(null)
            .fragment(null)
            .path("/login/ott")
            .queryParam("token", oneTimeToken.getTokenValue())
        val magicLink = builder.toUriString()
        val email = getUserEmail(oneTimeToken.getUsername())
        this.mailSender.send(
            SimpleMailMessage().apply {
                setTo(email)
                text = """Your Spring Security One Time Token", "Use the following link to sign in into the application: $magicLink"""
            }
        )
        // return 201 created
        response.status = HttpServletResponse.SC_CREATED
        response.contentType = "application/json"
        response.writer.write(
            """{"status":"ok","message":"A magic link has been sent to your email address. Please check your inbox."}"""
        )
    }

    private fun getUserEmail(username: String): String {
        return username
    }
}
