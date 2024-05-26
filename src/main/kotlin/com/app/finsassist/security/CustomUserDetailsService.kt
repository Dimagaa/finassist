package com.app.finsassist.security

import com.app.finsassist.exception.EntityNotFoundException
import com.app.finsassist.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.getByEmail(username).orElseThrow {
            EntityNotFoundException("User $username not found")
        }
}
