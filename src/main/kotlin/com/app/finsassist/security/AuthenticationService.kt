package com.app.finsassist.security

import com.app.finsassist.dto.user.AuthenticationRequest
import com.app.finsassist.dto.user.AuthenticationResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {
    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )
        return AuthenticationResponse(jwtService.generateToken(authentication.name))
    }
}
