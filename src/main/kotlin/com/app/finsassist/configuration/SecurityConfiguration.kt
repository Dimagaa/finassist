package com.app.finsassist.configuration

import com.app.finsassist.security.ExceptionHandlerFilter
import com.app.finsassist.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutFilter

private const val AUTH_ENDPOINT_MATCHER = "/auth/**"
private const val ERROR_PAGE_ENDPOINT_MATCHER = "/error"
private const val SWAGGER_UI_ENDPOINT_MATCHER = "/swagger-ui/**"
private const val SWAGGER_API_ENDPOINT_MATCHER = "/v3/api-docs/**"
private const val HEALTHCHECK_ENDPOINT_MATCHER = "/healthcheck"
private const val USERS_ENDPOINT_MATCHER = "/users"

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val exceptionHandlerFilter: ExceptionHandlerFilter
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(
        configuration: AuthenticationConfiguration
    ): AuthenticationManager = configuration.authenticationManager

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain =
        httpSecurity.cors { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(
                    AUTH_ENDPOINT_MATCHER,
                    ERROR_PAGE_ENDPOINT_MATCHER,
                    SWAGGER_UI_ENDPOINT_MATCHER,
                    SWAGGER_API_ENDPOINT_MATCHER,
                    HEALTHCHECK_ENDPOINT_MATCHER
                )
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, USERS_ENDPOINT_MATCHER)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .httpBasic(Customizer.withDefaults())
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(exceptionHandlerFilter, LogoutFilter::class.java)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .userDetailsService(userDetailsService)
            .build()
}
