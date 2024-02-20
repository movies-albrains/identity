package io.albrains.moviebrains.identity.main.user.controller

import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/password-reset")
class PasswordController {

    @PutMapping
    fun updatePassword(principal: Principal) {
        TODO("update password when connected (previous username + new username)")
    }

    @PutMapping("/confirm")
    fun confirmUpdatePassword() {
        TODO("update password with code")
    }
}