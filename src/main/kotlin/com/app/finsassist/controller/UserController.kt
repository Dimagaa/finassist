package com.app.finsassist.controller

import com.app.finsassist.dto.user.UserCreateRequest
import com.app.finsassist.model.User
import com.app.finsassist.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "Users management API",
    description = "Register and authenticate users"
)
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @Operation(
        summary = "Retrieve Current User Information",
        description = "Get Information of the Currently Authenticated User"
    )
    @GetMapping
    fun getInfo(@AuthenticationPrincipal user: User) = userService.getInfo(user)

    @Operation(
        summary = "Create a new user",
        description = "Create a new user with the provided information"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: UserCreateRequest) = userService.create(request)

    @Operation(
        summary = "Update User Information",
        description = "Update user with the provided information"
    )
    @PutMapping
    fun update(
        @AuthenticationPrincipal user: User,
        @RequestBody @Valid request: UserCreateRequest
    ) = userService.update(user.id, request)

    @Operation(
        summary = "Delete a user",
        description = "Delete a user with the provided id"
    )
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @AuthenticationPrincipal user: User
    ) = userService.delete(user.id)
}
