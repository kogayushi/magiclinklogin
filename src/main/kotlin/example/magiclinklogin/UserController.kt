package example.magiclinklogin

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {

    @GetMapping("/username")
    fun getUsername(@AuthenticationPrincipal userDetails: UserDetails): Map<String, String> {
        return mapOf("username" to userDetails.username)
    }
}
