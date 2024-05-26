package com.app.finsassist.repository

import com.app.finsassist.model.User
import java.util.Optional
import java.util.UUID
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, UUID> {
    @EntityGraph(attributePaths = ["permissions"])
    fun getByEmail(username: String): Optional<User>
}
