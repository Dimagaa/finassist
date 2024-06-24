package com.app.finsassist.util

import com.app.finsassist.model.Permission
import com.app.finsassist.model.User
import java.util.UUID

internal const val INSERT_USERS_SQL = "classpath:sql_scripts/insert-users.sql"
internal const val DELETE_USERS_SQL = "classpath:sql_scripts/delete-users.sql"

internal val TEST_PERMISSION = Permission(
    id = 1,
    name = Permission.Name.TEST
)

internal val BOB_USER = User(
    id = UUID.fromString("e3b3d183-62a1-4ffe-9bfb-1c62cbab3dd3"),
    email = "bob@test.test",
    password = "\$2a\$10\$GvHjWQqRzyrhpNvq5fX/ZuEa6UVyCLz1sGV/QITNvvJ1u9Cu1RYKm",
    firstName = "Bob",
    lastName = "Alison",
    permissions = setOf(TEST_PERMISSION),
    isDeleted = false
)

internal val ALICE_USER = User(
    id = UUID.fromString("3095e25c-479a-4e2a-b75e-0be2ff7c98a0"),
    email = "alice@test.test",
    password = "\$2a\$10\$GvHjWQqRzyrhpNvq5fX/ZuEa6UVyCLz1sGV/QITNvvJ1u9Cu1RYKm",
    firstName = "Alice",
    lastName = "Bobson",
    permissions = setOf(TEST_PERMISSION),
    isDeleted = false
)
