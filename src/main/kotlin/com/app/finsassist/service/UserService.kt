package com.app.finsassist.service

import com.app.finsassist.dto.user.UserDto
import com.app.finsassist.dto.user.UserCreateRequest
import com.app.finsassist.model.User
import java.util.UUID


interface UserService {
    fun get(user: User): UserDto

    fun create(request: UserCreateRequest): UserDto

    fun update(id: UUID?, request: UserCreateRequest): UserDto

    fun delete(id: UUID?)
}
