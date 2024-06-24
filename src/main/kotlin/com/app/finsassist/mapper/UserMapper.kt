package com.app.finsassist.mapper

import com.app.finsassist.configuration.MapperConfiguration
import com.app.finsassist.dto.user.UserCreateRequest
import com.app.finsassist.dto.user.UserDto
import com.app.finsassist.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = MapperConfiguration::class)
interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    fun toModel(request: UserCreateRequest): User

    fun toDto(user: User): UserDto
}
