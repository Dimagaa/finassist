package com.app.finsassist.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.Date
import javax.crypto.SecretKey
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secretString: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {
    private val logger = LoggerFactory.getLogger(JwtService::class.java)
    private val secret: SecretKey = Keys.hmacShaKeyFor(secretString.toByteArray())

    fun generateToken(userName: String): String =
        Jwts.builder()
            .subject(userName)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(secret)
            .compact()


    fun isValid(token: String): Boolean =
        runCatching {
            token.parse()
        }.fold(
            onSuccess = { true },
            onFailure = {
                logger.warn("Token validation error", it)
                false
            }
        )

    fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T =
        claimsResolver.invoke(token.parse().payload)

    private fun String.parse() =
        Jwts.parser()
            .decryptWith(secret)
            .build()
            .parseUnsecuredClaims(this)
}
