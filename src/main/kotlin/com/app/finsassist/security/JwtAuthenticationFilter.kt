package com.app.finsassist.security

import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private const val AUTHORIZATION_HEADER = "Authorization"
private const val BEARER = "Bearer "

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getAuthenticationToken()
        if (!token.isNullOrEmpty() && jwtService.isValid(token)) {
            val userName = jwtService.getClaimFromToken(token, Claims::getSubject)
            val userDetails = userDetailsService.loadUserByUsername(userName)
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
        }
        filterChain.doFilter(request, response)
    }

    private fun HttpServletRequest.getAuthenticationToken(): String? =
        getHeader(AUTHORIZATION_HEADER)
            ?.takeIf { it.startsWith(BEARER) }
            ?.removePrefix(BEARER)
}
