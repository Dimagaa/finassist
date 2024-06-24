package com.app.finsassist.dto.user

import com.app.finsassist.validation.EMAIL_REGEXP
import com.app.finsassist.validation.INVALID_PASSWORD_MESSAGE
import com.app.finsassist.validation.PASSWORD_REGEXP
import com.app.finsassist.validation.PropertiesMatch
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

@PropertiesMatch(
    message = "Passwords don't match",
    properties = ["password", "repeatedPassword"]
)
data class UserCreateRequest(
    @field:Email(regexp = EMAIL_REGEXP)
    @field:NotBlank
    @Schema(example = "test@test.com")
    val email: String,

    @Pattern(
        regexp = PASSWORD_REGEXP,
        message = INVALID_PASSWORD_MESSAGE
    )
    @field:NotBlank
    @Schema(example = "Pwd1234")
    val password: String,

    @field:NotBlank
    @Schema(example = "Pwd1234")
    val repeatedPassword: String,

    @field:Length(min = 3, max = 255)
    @Schema(example = "Bob")
    val firstName: String,

    @field:NotBlank
    @field:Length(min = 3, max = 255)
    @Schema(example = "Alison")
    val lastName: String,
)
