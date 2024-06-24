package com.app.finsassist.service.implementation

import com.app.finsassist.dto.user.UserCreateRequest
import com.app.finsassist.dto.user.UserDto
import com.app.finsassist.exception.UserCreationException
import com.app.finsassist.exception.EntityNotFoundException
import com.app.finsassist.mapper.UserMapper
import com.app.finsassist.model.User
import com.app.finsassist.repository.UserRepository
import com.app.finsassist.service.UserService
import jakarta.transaction.Transactional
import java.util.UUID
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun get(user: User): UserDto = userMapper.toDto(user)

    @Transactional
    override fun create(request: UserCreateRequest): UserDto {
        if (userRepository.getByEmail(request.email).isPresent) {
            throw UserCreationException("User already exists")
        }

        val user = userMapper.toModel(request).apply {
            password = passwordEncoder.encode(request.password)
        }
        return userMapper.toDto(userRepository.save(user))
    }

    @Transactional
    override fun update(id: UUID?, request: UserCreateRequest): UserDto {
        if (id == null) {
            throw EntityNotFoundException("User id is null")
        }
        val user = userRepository.getReferenceById(id).apply {
            email = request.email
            password = passwordEncoder.encode(request.password)
            firstName = request.firstName
            lastName = request.lastName
        }
        return userMapper.toDto(user)
    }

    @Transactional
    override fun delete(id: UUID?) {
        id?.let { userRepository.deleteById(id) }
    }
}
