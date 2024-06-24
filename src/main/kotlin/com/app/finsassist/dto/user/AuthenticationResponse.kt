package com.app.finsassist.dto.user

import io.swagger.v3.oas.annotations.media.Schema

data class AuthenticationResponse(
    @Schema(
        description = "A secure authentication token generated upon successful user login",
        example = "doNotShareYourApiKeyWithOthersOrExposeItInTheClientSideCode"
    )
    val token: String
)
