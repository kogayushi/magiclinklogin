package example.magiclinklogin

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MagicLinkLoginSuccessController {

    @PostMapping("/ott/sent")
    fun ottSent(): String {
        return "logged in!"
    }
}
