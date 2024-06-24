package com.app.finsassist.controller

import com.app.finsassist.dto.user.AuthenticationRequest
import com.app.finsassist.dto.user.AuthenticationResponse
import com.app.finsassist.security.AuthenticationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Authentication API", description = "Authenticate users")
@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @Operation(
        summary = "User authentication",
        description = "Authenticate a user with the provided credentials"
    )
    @PostMapping("/token")
    fun getToken(
        @RequestBody @Valid request: AuthenticationRequest
    ): AuthenticationResponse = authenticationService.authenticate(request)
}
