package com.app.finsassist.repository

import com.app.finsassist.util.BOB_USER
import com.app.finsassist.util.DELETE_USERS_SQL
import com.app.finsassist.util.INSERT_USERS_SQL
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.jdbc.Sql

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @Sql(
        scripts = [
            DELETE_USERS_SQL,
            INSERT_USERS_SQL
        ],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    fun getByEmail_WhenUserExist_ReturnOptionalOfUser() {
        //given
        val expected = BOB_USER

        //when
        val actual = userRepository.getByEmail(BOB_USER.email!!).get()

        //then
        Assertions.assertEquals(expected, actual)
        Assertions.assertEquals(expected.permissions, actual.permissions)
    }
}
