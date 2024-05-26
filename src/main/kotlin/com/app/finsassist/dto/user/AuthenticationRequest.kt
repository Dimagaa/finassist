package com.app.finsassist.dto.user

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AuthenticationRequest(
    @Email
    @NotBlank
    @Schema(example = "test@test.com")
    val email: String,

    @NotBlank
    @Schema(example = "Pwd1234")
    val password: String
)
