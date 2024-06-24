package com.app.finsassist.dto.user

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class UserDto(
    @Schema(description = "Generated user identity", example = "550e8400-e29b-41d4-a716-446655440000")
    val id: UUID,

    @Schema(example = "test@test.com")
    val email: String,

    @Schema(example = "Bob")
    val firstName: String,

    @Schema(example = "Alison")
    val lastName: String
)
