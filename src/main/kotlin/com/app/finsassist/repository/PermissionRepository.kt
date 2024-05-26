package com.app.finsassist.repository

import com.app.finsassist.model.Permission
import org.springframework.data.jpa.repository.JpaRepository

interface PermissionRepository : JpaRepository<Permission, Int> {
}
