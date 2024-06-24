package com.app.finsassist.dto.user

import com.app.finsassist.validation.EMAIL_REGEXP
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AuthenticationRequest(
    @field:Email(regexp = EMAIL_REGEXP)
    @field:NotBlank
    @Schema(example = "test@test.com")
    val email: String,

    @NotBlank
    @Schema(example = "Pwd1234")
    val password: String
)
